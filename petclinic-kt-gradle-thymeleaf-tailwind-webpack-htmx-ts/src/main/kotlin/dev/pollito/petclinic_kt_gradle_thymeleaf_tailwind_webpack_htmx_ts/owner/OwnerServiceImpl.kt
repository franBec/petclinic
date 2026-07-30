package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.owner

import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.events.BeforeDeleteOwner
import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.util.CustomCollectors
import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.util.NotFoundException
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class OwnerServiceImpl(
    private val ownerRepository: OwnerRepository,
    private val publisher: ApplicationEventPublisher,
) : OwnerService {

    override fun findAll(filter: String?, pageable: Pageable): Page<Owner> = if (filter != null) {
        ownerRepository.findAllById(filter.toIntOrNull(), pageable)
    } else {
        ownerRepository.findAll(pageable)
    }

    override fun `get`(id: Int): Owner = ownerRepository.findById(id)
        .orElseThrow { NotFoundException() }

    override fun create(owner: Owner): Int = ownerRepository.save(owner).id!!

    override fun update(id: Int, owner: Owner) {
        owner.id = id
        ownerRepository.findById(id).orElseThrow { NotFoundException() }
        ownerRepository.save(owner)
    }

    override fun delete(id: Int) {
        val owner = ownerRepository.findById(id)
            .orElseThrow { NotFoundException() }
        publisher.publishEvent(BeforeDeleteOwner(id))
        ownerRepository.delete(owner)
    }

    override fun getOwnerValues(): Map<Int, Int> = ownerRepository.findAll(Sort.by("id"))
        .stream()
        .collect(CustomCollectors.toSortedMap(Owner::id, Owner::id))
}
