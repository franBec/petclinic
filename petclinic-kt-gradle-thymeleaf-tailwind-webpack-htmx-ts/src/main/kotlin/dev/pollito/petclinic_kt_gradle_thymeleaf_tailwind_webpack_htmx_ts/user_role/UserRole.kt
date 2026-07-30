package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.user_role

import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.role.Role
import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.user.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table


@Entity
@Table(name = "user_roles")
class UserRole {

    @Id
    @Column(
        nullable = false,
        updatable = false
    )
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "username",
        nullable = false
    )
    var username: User? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "role_id",
        nullable = false
    )
    var role: Role? = null

}
