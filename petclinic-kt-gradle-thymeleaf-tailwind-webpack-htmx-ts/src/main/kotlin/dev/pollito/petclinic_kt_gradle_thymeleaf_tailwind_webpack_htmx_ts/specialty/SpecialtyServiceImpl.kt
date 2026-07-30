package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.specialty

import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.events.BeforeDeleteSpecialty
import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.util.CustomCollectors
import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.util.NotFoundException
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class SpecialtyServiceImpl(
    private val specialtyRepository: SpecialtyRepository,
    private val publisher: ApplicationEventPublisher,
) : SpecialtyService {

    override fun findAll(): List<Specialty> = specialtyRepository.findAll(Sort.by("id"))

    override fun `get`(id: Int): Specialty = specialtyRepository.findById(id)
        .orElseThrow { NotFoundException() }

    override fun create(specialty: Specialty): Int = specialtyRepository.save(specialty).id!!

    override fun update(id: Int, specialty: Specialty) {
        specialty.id = id
        specialtyRepository.findById(id).orElseThrow { NotFoundException() }
        specialtyRepository.save(specialty)
    }

    override fun delete(id: Int) {
        val specialty = specialtyRepository.findById(id)
            .orElseThrow { NotFoundException() }
        publisher.publishEvent(BeforeDeleteSpecialty(id))
        specialtyRepository.delete(specialty)
    }

    override fun getSpecialtyValues(): Map<Int, Int> = specialtyRepository.findAll(Sort.by("id"))
        .stream()
        .collect(CustomCollectors.toSortedMap(Specialty::id, Specialty::id))
}
