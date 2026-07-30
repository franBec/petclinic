package dev.pollito.petclinic_java_gradle_react_tailwind_ts.config;

import static org.springframework.security.config.Customizer.withDefaults;

import dev.pollito.petclinic_java_gradle_react_tailwind_ts.security.AdminTokenService;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.security.AdminUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class AdminSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // creates hashes with {bcrypt} prefix
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            final AuthenticationConfiguration authenticationConfiguration) {
        return authenticationConfiguration.getAuthenticationManager();
    }

    public JwtRequestFilter jwtRequestFilter(final AdminUserDetailsService adminUserDetailsService,
            final AdminTokenService adminTokenService) {
        return new JwtRequestFilter(adminUserDetailsService, adminTokenService);
    }

    @Bean
    public SecurityFilterChain adminFilterChain(final HttpSecurity http,
            final AdminUserDetailsService adminUserDetailsService,
            final AdminTokenService adminTokenService) {
        return http.cors(withDefaults())
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()))
                .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtRequestFilter(adminUserDetailsService, adminTokenService),
                        UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
