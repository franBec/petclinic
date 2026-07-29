package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.config

import org.springframework.beans.propertyeditors.StringTrimmerEditor
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.InitBinder


@ControllerAdvice
class ControllerConfig {

    @InitBinder
    fun initBinder(binder: WebDataBinder) {
        binder.registerCustomEditor(String::class.java, StringTrimmerEditor(true))
    }

}
