package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.type

import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.pet.Pet
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.SequenceGenerator
import java.time.OffsetDateTime


@Entity
class Type {

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

    @Column(
        nullable = false,
        columnDefinition = "text"
    )
    var name: String? = null

    @Column(nullable = false)
    var createdAt: OffsetDateTime? = null

    @Column(nullable = false)
    var updatedAt: OffsetDateTime? = null

    @Column
    var deletedAt: OffsetDateTime? = null

    @OneToMany(mappedBy = "type")
    var typePets = mutableSetOf<Pet>()

}
