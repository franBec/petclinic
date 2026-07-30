package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.vet

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface VetService {

    fun findAll(filter: String?, pageable: Pageable): Page<VetDTO>

    fun `get`(id: Int): VetDTO

    fun create(vetDTO: VetDTO): Int

    fun update(id: Int, vetDTO: VetDTO)

    fun delete(id: Int)
}
