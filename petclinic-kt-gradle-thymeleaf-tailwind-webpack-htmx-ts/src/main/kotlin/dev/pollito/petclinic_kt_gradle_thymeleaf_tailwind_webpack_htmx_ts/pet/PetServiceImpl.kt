package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.pet

import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.events.BeforeDeleteOwner
import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.events.BeforeDeletePet
import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.events.BeforeDeleteType
import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.owner.OwnerRepository
import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.type.TypeRepository
import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.util.CustomCollectors
import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.util.NotFoundException
import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.util.ReferencedException
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.event.EventListener
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service


@Service
class PetServiceImpl(
    private val petRepository: PetRepository,
    private val typeRepository: TypeRepository,
    private val ownerRepository: OwnerRepository,
    private val publisher: ApplicationEventPublisher,
    private val petMapper: PetMapper
) : PetService {

    override fun findAll(): List<PetDTO> {
        val pets = petRepository.findAll(Sort.by("id"))
        return pets.map { pet -> petMapper.updatePetDTO(pet, PetDTO()) }
    }

    override fun `get`(id: Int): PetDTO = petRepository.findById(id)
            .map { pet -> petMapper.updatePetDTO(pet, PetDTO()) }
            .orElseThrow { NotFoundException() }

    override fun create(petDTO: PetDTO): Int {
        val pet = Pet()
        petMapper.updatePet(petDTO, pet, typeRepository, ownerRepository)
        return petRepository.save(pet).id!!
    }

    override fun update(id: Int, petDTO: PetDTO) {
        val pet = petRepository.findById(id)
                .orElseThrow { NotFoundException() }
        petMapper.updatePet(petDTO, pet, typeRepository, ownerRepository)
        petRepository.save(pet)
    }

    override fun delete(id: Int) {
        val pet = petRepository.findById(id)
                .orElseThrow { NotFoundException() }
        publisher.publishEvent(BeforeDeletePet(id))
        petRepository.delete(pet)
    }

    override fun getPetValues(): Map<Int, Int> = petRepository.findAll(Sort.by("id"))
            .stream()
            .collect(CustomCollectors.toSortedMap(Pet::id, Pet::id))

    @EventListener(BeforeDeleteType::class)
    fun on(event: BeforeDeleteType) {
        val referencedException = ReferencedException()
        val typePet = petRepository.findFirstByTypeId(event.id)
        if (typePet != null) {
            referencedException.key = "type.pet.type.referenced"
            referencedException.addParam(typePet.id)
            throw referencedException
        }
    }

    @EventListener(BeforeDeleteOwner::class)
    fun on(event: BeforeDeleteOwner) {
        val referencedException = ReferencedException()
        val ownerPet = petRepository.findFirstByOwnerId(event.id)
        if (ownerPet != null) {
            referencedException.key = "owner.pet.owner.referenced"
            referencedException.addParam(ownerPet.id)
            throw referencedException
        }
    }

}
