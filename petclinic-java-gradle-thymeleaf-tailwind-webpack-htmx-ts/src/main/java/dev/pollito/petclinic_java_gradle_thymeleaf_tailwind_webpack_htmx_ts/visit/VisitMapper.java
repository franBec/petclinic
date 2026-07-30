package dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.visit;

import dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.pet.Pet;
import dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.pet.PetRepository;
import dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.util.NotFoundException;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class VisitMapper {

    @Autowired
    protected PetRepository petRepository;

    @Mapping(target = "pet", expression = "java(visit.getPet() == null ? null : visit.getPet().getId())")
    public abstract VisitDTO map(Visit visit);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pet", ignore = true)
    public abstract Visit map(VisitDTO visitDTO);

    @AfterMapping
    protected void afterMap(VisitDTO visitDTO, @MappingTarget Visit visit) {
        final Pet pet = visitDTO.getPet() == null ? null
                : petRepository.findById(visitDTO.getPet())
                        .orElseThrow(() -> new NotFoundException("pet not found"));
        visit.setPet(pet);
    }
}
