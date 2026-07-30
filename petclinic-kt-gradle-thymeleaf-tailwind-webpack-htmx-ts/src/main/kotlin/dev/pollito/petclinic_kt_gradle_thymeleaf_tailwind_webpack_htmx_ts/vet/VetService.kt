package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.vet

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface VetService {

    fun findAll(filter: String?, pageable: Pageable): Page<Vet>

    fun `get`(id: Int): Vet

    fun create(vet: Vet): Int

    fun update(id: Int, vet: Vet)

    fun delete(id: Int)
}
