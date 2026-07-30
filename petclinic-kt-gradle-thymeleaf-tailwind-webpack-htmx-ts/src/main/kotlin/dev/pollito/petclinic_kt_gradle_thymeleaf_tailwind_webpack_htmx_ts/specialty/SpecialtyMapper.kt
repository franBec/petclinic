package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.specialty

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingConstants
import org.mapstruct.ReportingPolicy

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
)
interface SpecialtyMapper {

    fun map(specialty: Specialty): SpecialtyDTO

    @Mapping(
        target = "id",
        ignore = true,
    )
    fun map(specialtyDTO: SpecialtyDTO): Specialty
}
