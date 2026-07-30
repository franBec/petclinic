package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.vet

import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.events.BeforeDeleteSpecialty
import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.specialty.SpecialtyRepository
import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.util.NotFoundException
import org.springframework.context.event.EventListener
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class VetServiceImpl(
    private val vetRepository: VetRepository,
    private val specialtyRepository: SpecialtyRepository,
    private val vetMapper: VetMapper,
) : VetService {

    override fun findAll(filter: String?, pageable: Pageable): Page<VetDTO> {
        var page: Page<Vet>
        if (filter != null) {
            page = vetRepository.findAllById(filter.toIntOrNull(), pageable)
        } else {
            page = vetRepository.findAll(pageable)
        }
        return PageImpl(
            page.content
                .map { vet -> vetMapper.updateVetDTO(vet, VetDTO()) },
            pageable,
            page.totalElements,
        )
    }

    override fun `get`(id: Int): VetDTO = vetRepository.findById(id)
        .map { vet -> vetMapper.updateVetDTO(vet, VetDTO()) }
        .orElseThrow { NotFoundException() }

    override fun create(vetDTO: VetDTO): Int {
        val vet = Vet()
        vetMapper.updateVet(vetDTO, vet, specialtyRepository)
        return vetRepository.save(vet).id!!
    }

    override fun update(id: Int, vetDTO: VetDTO) {
        val vet = vetRepository.findById(id)
            .orElseThrow { NotFoundException() }
        vetMapper.updateVet(vetDTO, vet, specialtyRepository)
        vetRepository.save(vet)
    }

    override fun delete(id: Int) {
        val vet = vetRepository.findById(id)
            .orElseThrow { NotFoundException() }
        vetRepository.delete(vet)
    }

    @EventListener(BeforeDeleteSpecialty::class)
    fun on(event: BeforeDeleteSpecialty) {
        // remove many-to-many relations at owning side
        vetRepository.findAllByVetSpecialtySpecialtiesId(event.id).forEach { vet ->
            vet.vetSpecialtySpecialties.removeIf { specialty -> specialty.id == event.id }
        }
    }
}
