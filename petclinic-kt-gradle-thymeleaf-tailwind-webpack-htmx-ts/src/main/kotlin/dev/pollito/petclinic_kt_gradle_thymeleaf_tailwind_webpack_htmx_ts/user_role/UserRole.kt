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


@Entity
class UserRole {

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "username_id",
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
