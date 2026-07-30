package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.util

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(HttpStatus.CONFLICT)
class ReferencedException : RuntimeException() {

    var key: String? = null

    var params = mutableListOf<Any>()

    override val message: String?
        get() {
            var message = key!!
            if (params.isNotEmpty()) {
                message += "," + params.joinToString(",")
            }
            return message
        }

    fun addParam(`param`: Any?) {
        params.add(param!!)
    }
}
