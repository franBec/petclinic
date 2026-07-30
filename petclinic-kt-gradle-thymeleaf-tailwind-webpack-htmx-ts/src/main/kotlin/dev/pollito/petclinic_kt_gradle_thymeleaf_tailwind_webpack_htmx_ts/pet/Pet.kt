package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.pet

import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.owner.Owner
import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.type.Type
import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.visit.Visit
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import java.time.LocalDate
import java.time.OffsetDateTime


@Entity
@Table(name = "pets")
class Pet {

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
    var name: String? = null

    @Column(nullable = false)
    var birthDate: LocalDate? = null

    @Column(nullable = false)
    var createdAt: OffsetDateTime? = null

    @Column(nullable = false)
    var updatedAt: OffsetDateTime? = null

    @Column
    var deletedAt: OffsetDateTime? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "type_id",
        nullable = false
    )
    var type: Type? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "owner_id",
        nullable = false
    )
    var owner: Owner? = null

    @OneToMany(mappedBy = "pet")
    var petVisits = mutableSetOf<Visit>()

}
