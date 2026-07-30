package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController {

    @GetMapping("/")
    fun index(): String = "home/index"
}
