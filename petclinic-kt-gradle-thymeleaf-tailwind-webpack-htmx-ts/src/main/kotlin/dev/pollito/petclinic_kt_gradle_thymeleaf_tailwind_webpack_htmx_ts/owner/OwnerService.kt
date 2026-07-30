package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.owner

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface OwnerService {

    fun findAll(filter: String?, pageable: Pageable): Page<Owner>

    fun `get`(id: Int): Owner

    fun create(owner: Owner): Int

    fun update(id: Int, owner: Owner)

    fun delete(id: Int)

    fun getOwnerValues(): Map<Int, Int>
}
