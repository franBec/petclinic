package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.pet

import org.springframework.data.jpa.repository.JpaRepository

interface PetRepository : JpaRepository<Pet, Int> {

    fun findFirstByTypeId(id: Int): Pet?

    fun findFirstByOwnerId(id: Int): Pet?
}
