package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.pet

import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.owner.OwnerRepository
import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.type.TypeRepository
import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.util.NotFoundException
import org.mapstruct.AfterMapping
import org.mapstruct.Context
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingConstants
import org.mapstruct.MappingTarget
import org.mapstruct.ReportingPolicy

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
)
interface PetMapper {

    @Mapping(
        target = "type",
        ignore = true,
    )
    @Mapping(
        target = "owner",
        ignore = true,
    )
    fun updatePetDTO(pet: Pet, @MappingTarget petDTO: PetDTO): PetDTO

    @AfterMapping
    fun afterUpdatePetDTO(pet: Pet, @MappingTarget petDTO: PetDTO) {
        petDTO.type = pet.type?.id
        petDTO.owner = pet.owner?.id
    }

    @Mapping(
        target = "id",
        ignore = true,
    )
    @Mapping(
        target = "type",
        ignore = true,
    )
    @Mapping(
        target = "owner",
        ignore = true,
    )
    fun updatePet(
        petDTO: PetDTO,
        @MappingTarget pet: Pet,
        @Context typeRepository: TypeRepository,
        @Context ownerRepository: OwnerRepository,
    ): Pet

    @AfterMapping
    fun afterUpdatePet(
        petDTO: PetDTO,
        @MappingTarget pet: Pet,
        @Context typeRepository: TypeRepository,
        @Context ownerRepository: OwnerRepository,
    ) {
        val type = if (petDTO.type == null) {
            null
        } else {
            typeRepository.findById(petDTO.type!!)
                .orElseThrow { NotFoundException("type not found") }
        }
        pet.type = type
        val owner = if (petDTO.owner == null) {
            null
        } else {
            ownerRepository.findById(petDTO.owner!!)
                .orElseThrow { NotFoundException("owner not found") }
        }
        pet.owner = owner
    }
}
