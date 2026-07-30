package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.visit

import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.pet.PetRepository
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
abstract class VisitMapper {

    @Autowired
    protected lateinit var petRepository: PetRepository

    @Mapping(
        target = "pet",
        expression = "java(visit.getPet() == null ? null : visit.getPet().getId())",
    )
    abstract fun map(visit: Visit): VisitDTO

    @Mapping(
        target = "id",
        ignore = true,
    )
    @Mapping(
        target = "pet",
        ignore = true,
    )
    abstract fun map(visitDTO: VisitDTO): Visit

    @AfterMapping
    protected fun afterMap(visitDTO: VisitDTO, @MappingTarget visit: Visit) {
        val pet = if (visitDTO.pet == null) {
            null
        } else {
            petRepository.findById(visitDTO.pet!!)
                .orElseThrow { NotFoundException("pet not found") }
        }
        visit.pet = pet
    }
}
