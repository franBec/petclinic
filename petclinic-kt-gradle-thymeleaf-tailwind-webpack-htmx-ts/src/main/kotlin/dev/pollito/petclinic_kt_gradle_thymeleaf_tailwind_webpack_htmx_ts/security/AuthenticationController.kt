package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.security

import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.util.WebUtils
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam


@Controller
class AuthenticationController {

    @GetMapping("/login")
    fun login(
        @RequestParam(name = "loginRequired", defaultValue = "false") loginRequired: Boolean,
        @RequestParam(name = "loginError", defaultValue = "false") loginError: Boolean,
        model: Model
    ): String {
        // dummy for using the inputRow fragment
        model.addAttribute("authentication", AuthenticationRequest())
        if (loginRequired) {
            model.addAttribute(WebUtils.MSG_INFO,
                    WebUtils.getMessage("authentication.login.required"))
        }
        if (loginError) {
            model.addAttribute(WebUtils.MSG_ERROR,
                    WebUtils.getMessage("authentication.login.error"))
        }
        return "authentication/login"
    }

}
