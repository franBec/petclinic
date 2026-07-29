package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.security

import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.user.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


@Service
class AdminUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsernameIgnoreCase(username)
        if (user == null) {
            log.warn("user not found: {}", username)
            throw UsernameNotFoundException("User ${username} not found")
        }
        val role = UserRoles.ROLE_UNKNOWN
        val authorities = listOf(SimpleGrantedAuthority(role))
        return User.withUsername(username)
                .password(user.password)
                .authorities(authorities)
                .build()
    }


    companion object {

        val log: Logger = LoggerFactory.getLogger(AdminUserDetailsService::class.java)

    }

}
