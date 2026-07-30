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
    private val specialtyMapper: SpecialtyMapper,
) : SpecialtyService {

    override fun findAll(): List<SpecialtyDTO> {
        val specialties = specialtyRepository.findAll(Sort.by("id"))
        return specialties.map { specialty ->
            specialtyMapper.updateSpecialtyDTO(
                specialty,
                SpecialtyDTO(),
            )
        }
    }

    override fun `get`(id: Int): SpecialtyDTO = specialtyRepository.findById(id)
        .map { specialty -> specialtyMapper.updateSpecialtyDTO(specialty, SpecialtyDTO()) }
        .orElseThrow { NotFoundException() }

    override fun create(specialtyDTO: SpecialtyDTO): Int {
        val specialty = Specialty()
        specialtyMapper.updateSpecialty(specialtyDTO, specialty)
        return specialtyRepository.save(specialty).id!!
    }

    override fun update(id: Int, specialtyDTO: SpecialtyDTO) {
        val specialty = specialtyRepository.findById(id)
            .orElseThrow { NotFoundException() }
        specialtyMapper.updateSpecialty(specialtyDTO, specialty)
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
