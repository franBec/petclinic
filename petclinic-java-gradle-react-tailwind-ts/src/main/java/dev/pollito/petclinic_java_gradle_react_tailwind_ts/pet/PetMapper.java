package dev.pollito.petclinic_java_gradle_react_tailwind_ts.pet;

import dev.pollito.petclinic_java_gradle_react_tailwind_ts.generated.model.PetDTO;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.owner.Owner;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.owner.OwnerRepository;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.type.Type;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.type.TypeRepository;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.util.NotFoundException;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class PetMapper {

    @Autowired
    protected TypeRepository typeRepository;

    @Autowired
    protected OwnerRepository ownerRepository;

    @Mapping(target = "type", expression = "java(pet.getType() == null ? null : pet.getType().getId())")
    @Mapping(target = "owner", expression = "java(pet.getOwner() == null ? null : pet.getOwner().getId())")
    public abstract PetDTO map(Pet pet);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "owner", ignore = true)
    public abstract Pet map(PetDTO petDTO);

    @AfterMapping
    protected void afterMap(PetDTO petDTO, @MappingTarget Pet pet) {
        final Type type = petDTO.getType() == null ? null
                : typeRepository.findById(petDTO.getType())
                        .orElseThrow(() -> new NotFoundException("type not found"));
        pet.setType(type);
        final Owner owner = petDTO.getOwner() == null ? null
                : ownerRepository.findById(petDTO.getOwner())
                        .orElseThrow(() -> new NotFoundException("owner not found"));
        pet.setOwner(owner);
    }
}
