package dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.pet;

import dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.events.BeforeDeleteOwner;
import dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.events.BeforeDeletePet;
import dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.events.BeforeDeleteType;
import dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.util.CustomCollectors;
import dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.util.NotFoundException;
import dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.util.ReferencedException;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final ApplicationEventPublisher publisher;

    @Override
    public List<Pet> findAll() {
        return petRepository.findAll(Sort.by("id"));
    }

    @Override
    public Pet get(final Integer id) {
        return petRepository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Integer create(final Pet pet) {
        return petRepository.save(pet).getId();
    }

    @Override
    public void update(final Integer id, final Pet pet) {
        pet.setId(id);
        petRepository.findById(id).orElseThrow(NotFoundException::new);
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
