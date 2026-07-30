package dev.pollito.petclinic_java_gradle_react_tailwind_ts.owner;

import dev.pollito.petclinic_java_gradle_react_tailwind_ts.generated.model.OwnerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OwnerMapper {

    OwnerDTO updateOwnerDTO(Owner owner, @MappingTarget OwnerDTO ownerDTO);

    @Mapping(target = "id", ignore = true)
    Owner updateOwner(OwnerDTO ownerDTO, @MappingTarget Owner owner);

}
