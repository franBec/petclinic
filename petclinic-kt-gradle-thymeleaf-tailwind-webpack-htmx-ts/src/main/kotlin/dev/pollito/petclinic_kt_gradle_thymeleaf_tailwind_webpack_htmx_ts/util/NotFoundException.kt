package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.util

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(HttpStatus.NOT_FOUND)
class NotFoundException : RuntimeException {

    constructor() : super()

    constructor(message: String) : super(message)
}
