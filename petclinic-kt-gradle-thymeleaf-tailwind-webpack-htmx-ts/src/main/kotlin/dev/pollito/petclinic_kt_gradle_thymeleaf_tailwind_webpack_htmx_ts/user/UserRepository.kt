package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.user

import org.springframework.data.jpa.repository.JpaRepository


interface UserRepository : JpaRepository<User, String> {

    fun findByUsernameIgnoreCase(username: String): User?

    fun findFirstByOwnerId(id: Int): User?

    fun existsByUsernameIgnoreCase(username: String?): Boolean

}
