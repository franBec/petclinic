package dev.pollito.petclinic_java_gradle_react_tailwind_ts.security;

import dev.pollito.petclinic_java_gradle_react_tailwind_ts.user.User;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.user.UserRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AdminUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public AdminUserDetailsService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) {
        final User user = userRepository.findByUsernameIgnoreCase(username);
        if (user == null) {
            log.warn("user not found: {}", username);
            throw new UsernameNotFoundException("User " + username + " not found");
        }

        return org.springframework.security.core.userdetails.User.withUsername(username)
                .password(user.getPassword())
                .authorities(user.getUsernameUserRoles().stream()
                        .map(ur -> new SimpleGrantedAuthority(ur.getRole().getName()))
                        .toList())
                .build();
    }

}
