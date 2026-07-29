package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.vet

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository


interface VetRepository : JpaRepository<Vet, Int> {

    fun findAllById(id: Int?, pageable: Pageable): Page<Vet>

    fun findAllByVetSpecialtySpecialtiesId(id: Int): List<Vet>

}
