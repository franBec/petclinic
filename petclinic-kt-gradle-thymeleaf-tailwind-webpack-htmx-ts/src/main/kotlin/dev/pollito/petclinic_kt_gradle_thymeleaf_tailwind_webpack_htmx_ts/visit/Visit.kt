package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.visit

import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.pet.Pet
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.SequenceGenerator
import java.time.LocalDate
import java.time.OffsetDateTime


@Entity
class Visit {

    @Id
    @Column(
        nullable = false,
        updatable = false
    )
    @SequenceGenerator(
        name = "primary_sequence",
        sequenceName = "primary_sequence",
        allocationSize = 1,
        initialValue = 10000
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "primary_sequence"
    )
    var id: Int? = null

    @Column(nullable = false)
    var visitDate: LocalDate? = null

    @Column(
        nullable = false,
        columnDefinition = "text"
    )
    var description: String? = null

    @Column(nullable = false)
    var createdAt: OffsetDateTime? = null

    @Column(nullable = false)
    var updatedAt: OffsetDateTime? = null

    @Column
    var deletedAt: OffsetDateTime? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "pet_id",
        nullable = false
    )
    var pet: Pet? = null

}
