package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.type

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingConstants
import org.mapstruct.ReportingPolicy

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
)
interface TypeMapper {

    fun map(type: Type): TypeDTO

    @Mapping(
        target = "id",
        ignore = true,
    )
    fun map(typeDTO: TypeDTO): Type
}
