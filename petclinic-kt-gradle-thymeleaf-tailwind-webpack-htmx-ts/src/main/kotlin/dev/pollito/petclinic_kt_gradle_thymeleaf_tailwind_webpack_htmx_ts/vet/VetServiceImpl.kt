package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.vet

import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.events.BeforeDeleteSpecialty
import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.util.NotFoundException
import org.springframework.context.event.EventListener
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class VetServiceImpl(
    private val vetRepository: VetRepository,
) : VetService {

    override fun findAll(filter: String?, pageable: Pageable): Page<Vet> = if (filter != null) {
        vetRepository.findAllById(filter.toIntOrNull(), pageable)
    } else {
        vetRepository.findAll(pageable)
    }

    override fun `get`(id: Int): Vet = vetRepository.findById(id)
        .orElseThrow { NotFoundException() }

    override fun create(vet: Vet): Int = vetRepository.save(vet).id!!

    override fun update(id: Int, vet: Vet) {
        vet.id = id
        vetRepository.findById(id).orElseThrow { NotFoundException() }
        vetRepository.save(vet)
    }

    override fun delete(id: Int) {
        val vet = vetRepository.findById(id)
            .orElseThrow { NotFoundException() }
        vetRepository.delete(vet)
    }

    @EventListener(BeforeDeleteSpecialty::class)
    fun on(event: BeforeDeleteSpecialty) {
        vetRepository.findAllByVetSpecialtySpecialtiesId(event.id).forEach { vet ->
            vet.vetSpecialtySpecialties.removeIf { specialty -> specialty.id == event.id }
        }
    }
}
