package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.visit

import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.events.BeforeDeletePet
import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.pet.PetRepository
import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.util.NotFoundException
import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.util.ReferencedException
import org.springframework.context.event.EventListener
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service


@Service
class VisitServiceImpl(
    private val visitRepository: VisitRepository,
    private val petRepository: PetRepository,
    private val visitMapper: VisitMapper
) : VisitService {

    override fun findAll(): List<VisitDTO> {
        val visits = visitRepository.findAll(Sort.by("id"))
        return visits.map { visit -> visitMapper.updateVisitDTO(visit, VisitDTO()) }
    }

    override fun `get`(id: Int): VisitDTO = visitRepository.findById(id)
            .map { visit -> visitMapper.updateVisitDTO(visit, VisitDTO()) }
            .orElseThrow { NotFoundException() }

    override fun create(visitDTO: VisitDTO): Int {
        val visit = Visit()
        visitMapper.updateVisit(visitDTO, visit, petRepository)
        return visitRepository.save(visit).id!!
    }

    override fun update(id: Int, visitDTO: VisitDTO) {
        val visit = visitRepository.findById(id)
                .orElseThrow { NotFoundException() }
        visitMapper.updateVisit(visitDTO, visit, petRepository)
        visitRepository.save(visit)
    }

    override fun delete(id: Int) {
        val visit = visitRepository.findById(id)
                .orElseThrow { NotFoundException() }
        visitRepository.delete(visit)
    }

    @EventListener(BeforeDeletePet::class)
    fun on(event: BeforeDeletePet) {
        val referencedException = ReferencedException()
        val petVisit = visitRepository.findFirstByPetId(event.id)
        if (petVisit != null) {
            referencedException.key = "pet.visit.pet.referenced"
            referencedException.addParam(petVisit.id)
            throw referencedException
        }
    }

}
