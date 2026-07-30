package dev.pollito.petclinic_java_gradle_react_tailwind_ts.owner;

import dev.pollito.petclinic_java_gradle_react_tailwind_ts.events.BeforeDeleteOwner;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.util.CustomCollectors;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.util.NotFoundException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;
    private final ApplicationEventPublisher publisher;

    @Override
    public Page<Owner> findAll(final String filter, final Pageable pageable) {
        if (filter != null) {
            Integer integerFilter = null;
            try {
                integerFilter = Integer.parseInt(filter);
            } catch (final NumberFormatException numberFormatException) {
            }
            return ownerRepository.findAllById(integerFilter, pageable);
        }
        return ownerRepository.findAll(pageable);
    }

    @Override
    public Owner get(final Integer id) {
        return ownerRepository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Integer create(final Owner owner) {
        return ownerRepository.save(owner).getId();
    }

    @Override
    public void update(final Integer id, final Owner owner) {
        owner.setId(id);
        ownerRepository.findById(id).orElseThrow(NotFoundException::new);
        ownerRepository.save(owner);
    }

    @Override
    public void delete(final Integer id) {
        final Owner owner = ownerRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        publisher.publishEvent(new BeforeDeleteOwner(id));
        ownerRepository.delete(owner);
    }

    @Override
    public Map<Integer, Integer> getOwnerValues() {
        return ownerRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Owner::getId, Owner::getId));
    }
}
