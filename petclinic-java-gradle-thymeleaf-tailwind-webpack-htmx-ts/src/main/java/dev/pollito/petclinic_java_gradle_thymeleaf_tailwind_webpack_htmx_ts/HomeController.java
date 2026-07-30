package dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "home/index";
    }

}
