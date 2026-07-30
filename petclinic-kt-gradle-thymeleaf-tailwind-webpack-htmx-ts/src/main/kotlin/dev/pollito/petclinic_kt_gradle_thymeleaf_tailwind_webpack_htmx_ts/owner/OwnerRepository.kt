package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.owner

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface OwnerRepository : JpaRepository<Owner, Int> {

    fun findAllById(id: Int?, pageable: Pageable): Page<Owner>
}
