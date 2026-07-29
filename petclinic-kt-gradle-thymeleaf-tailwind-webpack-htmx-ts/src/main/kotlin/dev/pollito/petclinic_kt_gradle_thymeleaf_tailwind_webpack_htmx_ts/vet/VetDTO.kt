package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.vet

import jakarta.validation.constraints.NotNull
import java.time.OffsetDateTime
import org.springframework.format.annotation.DateTimeFormat


class VetDTO {

    var id: Int? = null

    @NotNull
    var firstName: String? = null

    @NotNull
    var lastName: String? = null

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    var createdAt: OffsetDateTime? = null

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    var updatedAt: OffsetDateTime? = null

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    var deletedAt: OffsetDateTime? = null

    var vetSpecialtySpecialties: List<Int>? = null

}
