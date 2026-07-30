package dev.pollito.petclinic_java_gradle_react_tailwind_ts.visit;

import dev.pollito.petclinic_java_gradle_react_tailwind_ts.pet.Pet;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.pet.PetRepository;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.util.NotFoundException;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VisitMapper {

    @Mapping(target = "pet", ignore = true)
    VisitDTO updateVisitDTO(Visit visit, @MappingTarget VisitDTO visitDTO);

    @AfterMapping
    default void afterUpdateVisitDTO(Visit visit, @MappingTarget VisitDTO visitDTO) {
        visitDTO.setPet(visit.getPet() == null ? null : visit.getPet().getId());
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pet", ignore = true)
    Visit updateVisit(VisitDTO visitDTO, @MappingTarget Visit visit,
            @Context PetRepository petRepository);

    @AfterMapping
    default void afterUpdateVisit(VisitDTO visitDTO, @MappingTarget Visit visit,
            @Context PetRepository petRepository) {
        final Pet pet = visitDTO.getPet() == null ? null
                : petRepository.findById(visitDTO.getPet())
                        .orElseThrow(() -> new NotFoundException("pet not found"));
        visit.setPet(pet);
    }

}
