package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.visit

import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.pet.PetRepository
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
interface VisitMapper {

    @Mapping(
        target = "pet",
        ignore = true,
    )
    fun updateVisitDTO(visit: Visit, @MappingTarget visitDTO: VisitDTO): VisitDTO

    @AfterMapping
    fun afterUpdateVisitDTO(visit: Visit, @MappingTarget visitDTO: VisitDTO) {
        visitDTO.pet = visit.pet?.id
    }

    @Mapping(
        target = "id",
        ignore = true,
    )
    @Mapping(
        target = "pet",
        ignore = true,
    )
    fun updateVisit(
        visitDTO: VisitDTO,
        @MappingTarget visit: Visit,
        @Context petRepository: PetRepository,
    ): Visit

    @AfterMapping
    fun afterUpdateVisit(
        visitDTO: VisitDTO,
        @MappingTarget visit: Visit,
        @Context petRepository: PetRepository,
    ) {
        val pet = if (visitDTO.pet == null) {
            null
        } else {
            petRepository.findById(visitDTO.pet!!)
                .orElseThrow { NotFoundException("pet not found") }
        }
        visit.pet = pet
    }
}
