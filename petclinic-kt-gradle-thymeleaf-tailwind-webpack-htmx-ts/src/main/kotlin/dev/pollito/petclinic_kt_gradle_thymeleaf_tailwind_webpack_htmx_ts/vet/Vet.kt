package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.vet

import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.specialty.Specialty
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import java.time.OffsetDateTime


@Entity
@Table(name = "vets")
class Vet {

    @Id
    @Column(
        nullable = false,
        updatable = false
    )
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Column(
        nullable = false,
        columnDefinition = "text"
    )
    var firstName: String? = null

    @Column(
        nullable = false,
        columnDefinition = "text"
    )
    var lastName: String? = null

    @Column(nullable = false)
    var createdAt: OffsetDateTime? = null

    @Column(nullable = false)
    var updatedAt: OffsetDateTime? = null

    @Column
    var deletedAt: OffsetDateTime? = null

    @ManyToMany
    @JoinTable(
        name = "vet_specialties",
        joinColumns = [
            JoinColumn(name = "vet_id")
        ],
        inverseJoinColumns = [
            JoinColumn(name = "specialty_id")
        ]
    )
    var vetSpecialtySpecialties = mutableSetOf<Specialty>()

}
