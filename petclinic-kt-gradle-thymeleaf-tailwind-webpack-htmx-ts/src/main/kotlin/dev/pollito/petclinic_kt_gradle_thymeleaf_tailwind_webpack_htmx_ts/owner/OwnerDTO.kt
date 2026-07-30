package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.owner

import jakarta.validation.constraints.NotNull
import org.springframework.format.annotation.DateTimeFormat
import java.time.OffsetDateTime

class OwnerDTO {

    var id: Int? = null

    @NotNull
    var firstName: String? = null

    @NotNull
    var lastName: String? = null

    @NotNull
    var address: String? = null

    @NotNull
    var city: String? = null

    @NotNull
    var telephone: String? = null

    @NotNull
    var email: String? = null

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    var createdAt: OffsetDateTime? = null

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    var updatedAt: OffsetDateTime? = null

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    var deletedAt: OffsetDateTime? = null
}
