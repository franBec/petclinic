package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.type

import jakarta.validation.constraints.NotNull
import org.springframework.format.annotation.DateTimeFormat
import java.time.OffsetDateTime

class TypeDTO {

    var id: Int? = null

    @NotNull
    var name: String? = null

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    var createdAt: OffsetDateTime? = null

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    var updatedAt: OffsetDateTime? = null

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    var deletedAt: OffsetDateTime? = null
}
