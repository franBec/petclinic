package dev.pollito.petclinic_java_gradle_react_tailwind_ts.owner;

import dev.pollito.petclinic_java_gradle_react_tailwind_ts.events.BeforeDeleteOwner;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.util.CustomCollectors;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.util.NotFoundException;
import java.util.Map;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;
    private final ApplicationEventPublisher publisher;
    private final OwnerMapper ownerMapper;

    public OwnerServiceImpl(final OwnerRepository ownerRepository,
            final ApplicationEventPublisher publisher, final OwnerMapper ownerMapper) {
        this.ownerRepository = ownerRepository;
        this.publisher = publisher;
        this.ownerMapper = ownerMapper;
    }

    @Override
    public Page<OwnerDTO> findAll(final String filter, final Pageable pageable) {
        Page<Owner> page;
        if (filter != null) {
            Integer integerFilter = null;
            try {
                integerFilter = Integer.parseInt(filter);
            } catch (final NumberFormatException numberFormatException) {
                // keep null - no parseable input
            }
            page = ownerRepository.findAllById(integerFilter, pageable);
        } else {
            page = ownerRepository.findAll(pageable);
        }
        return new PageImpl<>(page.getContent()
                .stream()
                .map(owner -> ownerMapper.updateOwnerDTO(owner, new OwnerDTO()))
                .toList(),
                pageable, page.getTotalElements());
    }

    @Override
    public OwnerDTO get(final Integer id) {
        return ownerRepository.findById(id)
                .map(owner -> ownerMapper.updateOwnerDTO(owner, new OwnerDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Integer create(final OwnerDTO ownerDTO) {
        final Owner owner = new Owner();
        ownerMapper.updateOwner(ownerDTO, owner);
        return ownerRepository.save(owner).getId();
    }

    @Override
    public void update(final Integer id, final OwnerDTO ownerDTO) {
        final Owner owner = ownerRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        ownerMapper.updateOwner(ownerDTO, owner);
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
