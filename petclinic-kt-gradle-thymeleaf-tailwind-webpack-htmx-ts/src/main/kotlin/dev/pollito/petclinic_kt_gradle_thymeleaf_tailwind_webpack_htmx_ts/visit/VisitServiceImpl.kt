package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.visit

import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.events.BeforeDeletePet
import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.util.NotFoundException
import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.util.ReferencedException
import org.springframework.context.event.EventListener
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class VisitServiceImpl(
    private val visitRepository: VisitRepository,
) : VisitService {

    override fun findAll(): List<Visit> = visitRepository.findAll(Sort.by("id"))

    override fun `get`(id: Int): Visit = visitRepository.findById(id)
        .orElseThrow { NotFoundException() }

    override fun create(visit: Visit): Int = visitRepository.save(visit).id!!

    override fun update(id: Int, visit: Visit) {
        visit.id = id
        visitRepository.findById(id).orElseThrow { NotFoundException() }
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
