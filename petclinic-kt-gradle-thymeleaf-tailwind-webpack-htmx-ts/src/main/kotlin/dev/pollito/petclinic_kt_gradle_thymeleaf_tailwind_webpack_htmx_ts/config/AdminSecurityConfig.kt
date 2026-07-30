package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import java.time.Duration

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
class AdminSecurityConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        // creates hashes with {bcrypt} prefix
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager = authenticationConfiguration.authenticationManager

    @Bean
    fun adminFilterChain(
        http: HttpSecurity,
        @Value("\${admin.rememberMeKey}")
        rememberMeKey: String,
    ): SecurityFilterChain = http.cors(Customizer.withDefaults())
        .csrf { csrf -> csrf.ignoringRequestMatchers("/actuator/**") }
        .authorizeHttpRequests { authorize -> authorize.anyRequest().permitAll() }
        .formLogin { form ->
            form
                .loginPage("/login")
                .failureUrl("/login?loginError=true")
        }
        .rememberMe { rememberMe ->
            rememberMe
                .tokenValiditySeconds(Duration.ofDays(180).getSeconds().toInt())
                .rememberMeParameter("rememberMe")
                .key(rememberMeKey)
        }
        .logout { logout ->
            logout
                .logoutSuccessUrl("/?logoutSuccess=true")
                .deleteCookies("JSESSIONID")
        }
        .exceptionHandling { exception ->
            exception
                .authenticationEntryPoint(LoginUrlAuthenticationEntryPoint("/login?loginRequired=true"))
        }
        .build()
}
