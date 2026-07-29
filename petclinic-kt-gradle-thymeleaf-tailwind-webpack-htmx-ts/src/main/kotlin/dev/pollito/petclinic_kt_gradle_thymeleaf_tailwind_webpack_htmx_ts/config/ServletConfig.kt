package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.config

import jakarta.servlet.SessionTrackingMode
import org.springframework.boot.web.servlet.ServletContextInitializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class ServletConfig {

    @Bean
    fun servletContextInitializer(): ServletContextInitializer {
        // don't append the session id to resources
        return ServletContextInitializer { servletContext ->
                servletContext.setSessionTrackingModes(setOf(SessionTrackingMode.COOKIE)) }
    }

}
