package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.owner

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingConstants
import org.mapstruct.MappingTarget
import org.mapstruct.ReportingPolicy

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
)
interface OwnerMapper {

    fun updateOwnerDTO(owner: Owner, @MappingTarget ownerDTO: OwnerDTO): OwnerDTO

    @Mapping(
        target = "id",
        ignore = true,
    )
    fun updateOwner(ownerDTO: OwnerDTO, @MappingTarget owner: Owner): Owner
}
