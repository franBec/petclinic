package dev.pollito.petclinic_java_gradle_react_tailwind_ts.specialty;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface SpecialtyMapper {

    SpecialtyDTO updateSpecialtyDTO(Specialty specialty, @MappingTarget SpecialtyDTO specialtyDTO);

    @Mapping(target = "id", ignore = true)
    Specialty updateSpecialty(SpecialtyDTO specialtyDTO, @MappingTarget Specialty specialty);

}
