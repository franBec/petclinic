package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.visit

import org.springframework.data.jpa.repository.JpaRepository


interface VisitRepository : JpaRepository<Visit, Int> {

    fun findFirstByPetId(id: Int): Visit?

}
