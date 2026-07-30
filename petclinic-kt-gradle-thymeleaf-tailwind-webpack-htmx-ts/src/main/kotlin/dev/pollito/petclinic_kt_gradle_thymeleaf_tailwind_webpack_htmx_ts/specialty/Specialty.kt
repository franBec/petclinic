package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.specialty

import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.vet.Vet
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table
import java.time.OffsetDateTime

@Entity
@Table(name = "specialties")
class Specialty {

    @Id
    @Column(
        nullable = false,
        updatable = false,
    )
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Column(
        nullable = false,
        columnDefinition = "text",
    )
    var name: String? = null

    @Column(nullable = false)
    var createdAt: OffsetDateTime? = null

    @Column(nullable = false)
    var updatedAt: OffsetDateTime? = null

    @Column
    var deletedAt: OffsetDateTime? = null

    @ManyToMany(mappedBy = "vetSpecialtySpecialties")
    var vetSpecialtyVets = mutableSetOf<Vet>()
}
