package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.role

import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.user_role.UserRole
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "roles")
class Role {

    @Id
    @Column(
        nullable = false,
        updatable = false,
    )
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Column(
        nullable = false,
        length = 20,
    )
    var name: String? = null

    @Column(columnDefinition = "text")
    var description: String? = null

    @OneToMany(mappedBy = "role")
    var roleUserRoles = mutableSetOf<UserRole>()
}
