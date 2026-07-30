package dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.security;

import dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthenticationController {

    @GetMapping("/login")
    public String login(
            @RequestParam(name = "loginRequired", defaultValue = "false") final Boolean loginRequired,
            @RequestParam(name = "loginError", defaultValue = "false") final Boolean loginError,
            @RequestParam(name = "logout", defaultValue = "false") final Boolean logout,
            final Model model) {
        // dummy for using the inputRow fragment
        model.addAttribute("authentication", new AuthenticationRequest());
        if (loginRequired) {
            model.addAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("authentication.login.required"));
        }
        if (loginError) {
            model.addAttribute(WebUtils.MSG_ERROR, WebUtils.getMessage("authentication.login.error"));
        }
        if (logout) {
            model.addAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("authentication.logout.success"));
        }
        return "authentication/login";
    }

}
