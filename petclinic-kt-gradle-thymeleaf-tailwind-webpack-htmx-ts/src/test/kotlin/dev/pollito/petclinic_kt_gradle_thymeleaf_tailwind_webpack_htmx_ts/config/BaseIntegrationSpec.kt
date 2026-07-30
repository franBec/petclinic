package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.config

import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.user.User
import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.ConfigurableMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.context.WebApplicationContext
import java.time.OffsetDateTime

@SpringBootTest
@ActiveProfiles("it")
@Transactional
abstract class BaseIntegrationSpec {

    protected lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var webApplicationContext: WebApplicationContext

    @Autowired
    protected lateinit var userRepository: UserRepository

    @Autowired
    protected lateinit var passwordEncoder: PasswordEncoder

    protected fun initMockMvc() {
        val builder: ConfigurableMockMvcBuilder<*> = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
        builder.apply(SecurityMockMvcConfigurers.springSecurity())
        mockMvc = builder.build()
    }

    protected fun createTestUser(username: String, password: String): User {
        val user = User()
        user.username = username
        user.password = passwordEncoder.encode(password)
        user.enabled = true
        user.createdAt = OffsetDateTime.now()
        user.updatedAt = OffsetDateTime.now()
        return userRepository.save(user)
    }
}
