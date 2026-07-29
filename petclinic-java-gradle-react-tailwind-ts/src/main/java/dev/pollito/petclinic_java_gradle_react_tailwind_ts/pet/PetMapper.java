package dev.pollito.petclinic_java_gradle_react_tailwind_ts.pet;

import dev.pollito.petclinic_java_gradle_react_tailwind_ts.owner.Owner;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.owner.OwnerRepository;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.type.Type;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.type.TypeRepository;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.util.NotFoundException;
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
public interface PetMapper {

    @Mapping(target = "type", ignore = true)
    @Mapping(target = "owner", ignore = true)
    PetDTO updatePetDTO(Pet pet, @MappingTarget PetDTO petDTO);

    @AfterMapping
    default void afterUpdatePetDTO(Pet pet, @MappingTarget PetDTO petDTO) {
        petDTO.setType(pet.getType() == null ? null : pet.getType().getId());
        petDTO.setOwner(pet.getOwner() == null ? null : pet.getOwner().getId());
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "owner", ignore = true)
    Pet updatePet(PetDTO petDTO, @MappingTarget Pet pet, @Context TypeRepository typeRepository,
            @Context OwnerRepository ownerRepository);

    @AfterMapping
    default void afterUpdatePet(PetDTO petDTO, @MappingTarget Pet pet,
            @Context TypeRepository typeRepository, @Context OwnerRepository ownerRepository) {
        final Type type = petDTO.getType() == null ? null : typeRepository.findById(petDTO.getType())
                .orElseThrow(() -> new NotFoundException("type not found"));
        pet.setType(type);
        final Owner owner = petDTO.getOwner() == null ? null : ownerRepository.findById(petDTO.getOwner())
                .orElseThrow(() -> new NotFoundException("owner not found"));
        pet.setOwner(owner);
    }

}
