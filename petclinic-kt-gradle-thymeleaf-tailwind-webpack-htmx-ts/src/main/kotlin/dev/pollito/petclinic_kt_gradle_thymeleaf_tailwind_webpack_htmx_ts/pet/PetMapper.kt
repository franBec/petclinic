package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.pet

import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.owner.OwnerRepository
import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.type.TypeRepository
import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.util.NotFoundException
import org.mapstruct.AfterMapping
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingConstants
import org.mapstruct.MappingTarget
import org.mapstruct.ReportingPolicy
import org.springframework.beans.factory.annotation.Autowired

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
)
abstract class PetMapper {

    @Autowired
    protected lateinit var typeRepository: TypeRepository

    @Autowired
    protected lateinit var ownerRepository: OwnerRepository

    @Mapping(
        target = "type",
        expression = "java(pet.getType() == null ? null : pet.getType().getId())",
    )
    @Mapping(
        target = "owner",
        expression = "java(pet.getOwner() == null ? null : pet.getOwner().getId())",
    )
    abstract fun map(pet: Pet): PetDTO

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
    abstract fun map(petDTO: PetDTO): Pet

    @AfterMapping
    protected fun afterMap(petDTO: PetDTO, @MappingTarget pet: Pet) {
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
