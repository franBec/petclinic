package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.owner

import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.events.BeforeDeleteOwner
import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.util.CustomCollectors
import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.util.NotFoundException
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class OwnerServiceImpl(
    private val ownerRepository: OwnerRepository,
    private val publisher: ApplicationEventPublisher,
    private val ownerMapper: OwnerMapper,
) : OwnerService {

    override fun findAll(filter: String?, pageable: Pageable): Page<OwnerDTO> {
        var page: Page<Owner>
        if (filter != null) {
            page = ownerRepository.findAllById(filter.toIntOrNull(), pageable)
        } else {
            page = ownerRepository.findAll(pageable)
        }
        return PageImpl(
            page.content
                .map { owner -> ownerMapper.updateOwnerDTO(owner, OwnerDTO()) },
            pageable,
            page.totalElements,
        )
    }

    override fun `get`(id: Int): OwnerDTO = ownerRepository.findById(id)
        .map { owner -> ownerMapper.updateOwnerDTO(owner, OwnerDTO()) }
        .orElseThrow { NotFoundException() }

    override fun create(ownerDTO: OwnerDTO): Int {
        val owner = Owner()
        ownerMapper.updateOwner(ownerDTO, owner)
        return ownerRepository.save(owner).id!!
    }

    override fun update(id: Int, ownerDTO: OwnerDTO) {
        val owner = ownerRepository.findById(id)
            .orElseThrow { NotFoundException() }
        ownerMapper.updateOwner(ownerDTO, owner)
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
