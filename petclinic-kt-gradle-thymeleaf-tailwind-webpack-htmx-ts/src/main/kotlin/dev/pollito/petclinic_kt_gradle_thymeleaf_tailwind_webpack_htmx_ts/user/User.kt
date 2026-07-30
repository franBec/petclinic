package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.user

import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.owner.Owner
import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.user_role.UserRole
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.OffsetDateTime

@Entity
@Table(name = "users")
class User {

    @Id
    @Column(
        nullable = false,
        updatable = false,
        length = 20,
    )
    var username: String? = null

    @Column(
        nullable = false,
        length = 68,
    )
    var password: String? = null

    @Column(nullable = false)
    var enabled: Boolean? = null

    @Column(nullable = false)
    var createdAt: OffsetDateTime? = null

    @Column(nullable = false)
    var updatedAt: OffsetDateTime? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    var owner: Owner? = null

    @OneToMany(mappedBy = "username")
    var usernameUserRoles = mutableSetOf<UserRole>()
}
