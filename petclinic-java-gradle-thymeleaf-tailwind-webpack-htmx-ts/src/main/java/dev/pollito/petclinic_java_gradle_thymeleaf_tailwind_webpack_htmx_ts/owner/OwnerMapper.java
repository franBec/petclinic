package dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.owner;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OwnerMapper {

    OwnerDTO map(Owner owner);

    @Mapping(target = "id", ignore = true)
    Owner map(OwnerDTO ownerDTO);
}
