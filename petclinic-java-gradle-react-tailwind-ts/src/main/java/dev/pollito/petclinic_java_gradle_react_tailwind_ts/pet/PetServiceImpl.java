package dev.pollito.petclinic_java_gradle_react_tailwind_ts.pet;

import dev.pollito.petclinic_java_gradle_react_tailwind_ts.events.BeforeDeleteOwner;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.events.BeforeDeletePet;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.events.BeforeDeleteType;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.owner.OwnerRepository;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.type.TypeRepository;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.util.CustomCollectors;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.util.NotFoundException;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.util.ReferencedException;
import java.util.List;
import java.util.Map;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final TypeRepository typeRepository;
    private final OwnerRepository ownerRepository;
    private final ApplicationEventPublisher publisher;
    private final PetMapper petMapper;

    public PetServiceImpl(final PetRepository petRepository, final TypeRepository typeRepository,
            final OwnerRepository ownerRepository, final ApplicationEventPublisher publisher,
            final PetMapper petMapper) {
        this.petRepository = petRepository;
        this.typeRepository = typeRepository;
        this.ownerRepository = ownerRepository;
        this.publisher = publisher;
        this.petMapper = petMapper;
    }

    @Override
    public List<PetDTO> findAll() {
        final List<Pet> pets = petRepository.findAll(Sort.by("id"));
        return pets.stream()
                .map(pet -> petMapper.updatePetDTO(pet, new PetDTO()))
                .toList();
    }

    @Override
    public PetDTO get(final Integer id) {
        return petRepository.findById(id)
                .map(pet -> petMapper.updatePetDTO(pet, new PetDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Integer create(final PetDTO petDTO) {
        final Pet pet = new Pet();
        petMapper.updatePet(petDTO, pet, typeRepository, ownerRepository);
        return petRepository.save(pet).getId();
    }

    @Override
    public void update(final Integer id, final PetDTO petDTO) {
        final Pet pet = petRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        petMapper.updatePet(petDTO, pet, typeRepository, ownerRepository);
        petRepository.save(pet);
    }

    @Override
    public void delete(final Integer id) {
        final Pet pet = petRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        publisher.publishEvent(new BeforeDeletePet(id));
        petRepository.delete(pet);
    }

    @Override
    public Map<Integer, Integer> getPetValues() {
        return petRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Pet::getId, Pet::getId));
    }

    @EventListener(BeforeDeleteType.class)
    public void on(final BeforeDeleteType event) {
        final ReferencedException referencedException = new ReferencedException();
        final Pet typePet = petRepository.findFirstByTypeId(event.getId());
        if (typePet != null) {
            referencedException.setKey("type.pet.type.referenced");
            referencedException.addParam(typePet.getId());
            throw referencedException;
        }
    }

    @EventListener(BeforeDeleteOwner.class)
    public void on(final BeforeDeleteOwner event) {
        final ReferencedException referencedException = new ReferencedException();
        final Pet ownerPet = petRepository.findFirstByOwnerId(event.getId());
        if (ownerPet != null) {
            referencedException.setKey("owner.pet.owner.referenced");
            referencedException.addParam(ownerPet.getId());
            throw referencedException;
        }
    }

}
