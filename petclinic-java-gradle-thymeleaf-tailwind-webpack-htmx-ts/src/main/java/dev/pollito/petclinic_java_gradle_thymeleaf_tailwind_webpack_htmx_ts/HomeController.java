package dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts;

import dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class HomeController {

    @GetMapping("/")
    public String index(
            @RequestParam(name = "logoutSuccess", defaultValue = "false") final Boolean logoutSuccess,
            final Model model) {
        if (logoutSuccess) {
            model.addAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("authentication.logout.success"));
        }
        return "home/index";
    }

}
