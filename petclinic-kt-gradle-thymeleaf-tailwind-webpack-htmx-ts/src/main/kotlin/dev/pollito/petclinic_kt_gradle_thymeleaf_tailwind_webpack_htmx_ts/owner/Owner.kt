package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.owner

import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.pet.Pet
import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.user.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.OffsetDateTime

@Entity
@Table(name = "owners")
class Owner {

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
    var firstName: String? = null

    @Column(
        nullable = false,
        columnDefinition = "text",
    )
    var lastName: String? = null

    @Column(
        nullable = false,
        columnDefinition = "text",
    )
    var address: String? = null

    @Column(
        nullable = false,
        columnDefinition = "text",
    )
    var city: String? = null

    @Column(
        nullable = false,
        columnDefinition = "text",
    )
    var telephone: String? = null

    @Column(
        nullable = false,
        columnDefinition = "text",
    )
    var email: String? = null

    @Column(nullable = false)
    var createdAt: OffsetDateTime? = null

    @Column(nullable = false)
    var updatedAt: OffsetDateTime? = null

    @Column
    var deletedAt: OffsetDateTime? = null

    @OneToMany(mappedBy = "owner")
    var ownerPets = mutableSetOf<Pet>()

    @OneToMany(mappedBy = "owner")
    var ownerUsers = mutableSetOf<User>()
}
