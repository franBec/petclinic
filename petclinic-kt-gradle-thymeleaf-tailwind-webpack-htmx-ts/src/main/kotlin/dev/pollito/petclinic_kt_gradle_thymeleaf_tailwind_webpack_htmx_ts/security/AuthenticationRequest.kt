package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.security

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size


class AuthenticationRequest {

    @NotNull
    @Size(max = 20)
    var username: String? = null

    @NotNull
    @Size(max = 72)
    var password: String? = null

}
