package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.owner

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface OwnerService {

    fun findAll(filter: String?, pageable: Pageable): Page<OwnerDTO>

    fun `get`(id: Int): OwnerDTO

    fun create(ownerDTO: OwnerDTO): Int

    fun update(id: Int, ownerDTO: OwnerDTO)

    fun delete(id: Int)

    fun getOwnerValues(): Map<Int, Int>
}
