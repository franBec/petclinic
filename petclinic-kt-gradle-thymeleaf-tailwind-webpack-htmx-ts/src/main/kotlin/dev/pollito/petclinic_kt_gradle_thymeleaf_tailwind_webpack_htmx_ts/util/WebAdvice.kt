package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.util

import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ModelAttribute


/**
 * Provide attributes available in all templates.
 */
@ControllerAdvice
class WebAdvice {

    @ModelAttribute("requestUri")
    fun getRequestUri(request: HttpServletRequest): String = request.requestURI

    @ModelAttribute("isDevserver")
    fun getIsDevserver(request: HttpServletRequest): Boolean =
            "1".equals(request.getHeader("X-Devserver"))

}
