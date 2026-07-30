package dev.pollito.petclinic_java_gradle_react_tailwind_ts.vet;

import dev.pollito.petclinic_java_gradle_react_tailwind_ts.generated.model.VetDTO;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.specialty.Specialty;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.specialty.SpecialtyRepository;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.util.NotFoundException;
import java.util.HashSet;
import java.util.List;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class VetMapper {

    @Autowired
    protected SpecialtyRepository specialtyRepository;

    @Mapping(target = "vetSpecialtySpecialties", expression = "java(vet.getVetSpecialtySpecialties() == null ? java.util.List.of() : vet.getVetSpecialtySpecialties().stream().map(specialty -> specialty.getId()).toList())")
    public abstract VetDTO map(Vet vet);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "vetSpecialtySpecialties", ignore = true)
    public abstract Vet map(VetDTO vetDTO);

    @AfterMapping
    protected void afterMap(VetDTO vetDTO, @MappingTarget Vet vet) {
        final List<Specialty> vetSpecialtySpecialties = specialtyRepository.findAllById(
                vetDTO.getVetSpecialtySpecialties() == null ? List.of() : vetDTO.getVetSpecialtySpecialties());
        if (vetSpecialtySpecialties.size() != (vetDTO.getVetSpecialtySpecialties() == null ? 0
                : vetDTO.getVetSpecialtySpecialties().size())) {
            throw new NotFoundException("one of vetSpecialtySpecialties not found");
        }
        vet.setVetSpecialtySpecialties(new HashSet<>(vetSpecialtySpecialties));
    }
}
