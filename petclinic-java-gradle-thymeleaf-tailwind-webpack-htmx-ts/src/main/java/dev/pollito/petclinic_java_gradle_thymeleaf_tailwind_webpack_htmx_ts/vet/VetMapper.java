package dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.vet;

import dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.specialty.Specialty;
import dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.specialty.SpecialtyRepository;
import dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.util.NotFoundException;
import java.util.HashSet;
import java.util.List;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface VetMapper {

    @Mapping(target = "vetSpecialtySpecialties", ignore = true)
    VetDTO updateVetDTO(Vet vet, @MappingTarget VetDTO vetDTO);

    @AfterMapping
    default void afterUpdateVetDTO(Vet vet, @MappingTarget VetDTO vetDTO) {
        vetDTO.setVetSpecialtySpecialties(vet.getVetSpecialtySpecialties().stream()
                .map(specialty -> specialty.getId())
                .toList());
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "vetSpecialtySpecialties", ignore = true)
    Vet updateVet(VetDTO vetDTO, @MappingTarget Vet vet,
            @Context SpecialtyRepository specialtyRepository);

    @AfterMapping
    default void afterUpdateVet(VetDTO vetDTO, @MappingTarget Vet vet,
            @Context SpecialtyRepository specialtyRepository) {
        final List<Specialty> vetSpecialtySpecialties = specialtyRepository.findAllById(
                vetDTO.getVetSpecialtySpecialties() == null ? List.of() : vetDTO.getVetSpecialtySpecialties());
        if (vetSpecialtySpecialties.size() != (vetDTO.getVetSpecialtySpecialties() == null ? 0 : vetDTO.getVetSpecialtySpecialties().size())) {
            throw new NotFoundException("one of vetSpecialtySpecialties not found");
        }
        vet.setVetSpecialtySpecialties(new HashSet<>(vetSpecialtySpecialties));
    }

}
