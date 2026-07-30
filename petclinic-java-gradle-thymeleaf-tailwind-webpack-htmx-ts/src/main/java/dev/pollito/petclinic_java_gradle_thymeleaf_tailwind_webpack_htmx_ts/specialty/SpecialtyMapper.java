package dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.specialty;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SpecialtyMapper {

    SpecialtyDTO map(Specialty specialty);

    @Mapping(target = "id", ignore = true)
    Specialty map(SpecialtyDTO specialtyDTO);
}
