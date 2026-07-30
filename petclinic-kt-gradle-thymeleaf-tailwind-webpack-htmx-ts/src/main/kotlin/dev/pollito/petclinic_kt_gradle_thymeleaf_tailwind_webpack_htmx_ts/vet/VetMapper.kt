package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.vet

import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.specialty.SpecialtyRepository
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
interface VetMapper {

    @Mapping(
        target = "vetSpecialtySpecialties",
        ignore = true,
    )
    fun updateVetDTO(vet: Vet, @MappingTarget vetDTO: VetDTO): VetDTO

    @AfterMapping
    fun afterUpdateVetDTO(vet: Vet, @MappingTarget vetDTO: VetDTO) {
        vetDTO.vetSpecialtySpecialties = vet.vetSpecialtySpecialties
            .map { specialty -> specialty.id!! }
    }

    @Mapping(
        target = "id",
        ignore = true,
    )
    @Mapping(
        target = "vetSpecialtySpecialties",
        ignore = true,
    )
    fun updateVet(
        vetDTO: VetDTO,
        @MappingTarget vet: Vet,
        @Context specialtyRepository: SpecialtyRepository,
    ): Vet

    @AfterMapping
    fun afterUpdateVet(
        vetDTO: VetDTO,
        @MappingTarget vet: Vet,
        @Context specialtyRepository: SpecialtyRepository,
    ) {
        val vetSpecialtySpecialties = specialtyRepository.findAllById(
            vetDTO.vetSpecialtySpecialties
                ?: emptyList(),
        )
        if (vetSpecialtySpecialties.size != (
                if (vetDTO.vetSpecialtySpecialties == null) {
                    0
                } else {
                    vetDTO.vetSpecialtySpecialties!!.size
                }
                )
        ) {
            throw NotFoundException("one of vetSpecialtySpecialties not found")
        }
        vet.vetSpecialtySpecialties = vetSpecialtySpecialties.toMutableSet()
    }
}
