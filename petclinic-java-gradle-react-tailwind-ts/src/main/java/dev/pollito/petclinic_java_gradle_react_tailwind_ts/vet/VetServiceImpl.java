package dev.pollito.petclinic_java_gradle_react_tailwind_ts.vet;

import dev.pollito.petclinic_java_gradle_react_tailwind_ts.events.BeforeDeleteSpecialty;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.util.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class VetServiceImpl implements VetService {

    private final VetRepository vetRepository;

    @Override
    public Page<Vet> findAll(final String filter, final Pageable pageable) {
        if (filter != null) {
            Integer integerFilter = null;
            try {
                integerFilter = Integer.parseInt(filter);
            } catch (final NumberFormatException numberFormatException) {
            }
            return vetRepository.findAllById(integerFilter, pageable);
        }
        return vetRepository.findAll(pageable);
    }

    @Override
    public Vet get(final Integer id) {
        return vetRepository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Integer create(final Vet vet) {
        return vetRepository.save(vet).getId();
    }

    @Override
    public void update(final Integer id, final Vet vet) {
        vet.setId(id);
        vetRepository.findById(id).orElseThrow(NotFoundException::new);
        vetRepository.save(vet);
    }

    @Override
    public void delete(final Integer id) {
        final Vet vet = vetRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        vetRepository.delete(vet);
    }

    @EventListener(BeforeDeleteSpecialty.class)
    public void on(final BeforeDeleteSpecialty event) {
        vetRepository.findAllByVetSpecialtySpecialtiesId(event.getId()).forEach(
                vet -> vet.getVetSpecialtySpecialties().removeIf(specialty -> specialty.getId().equals(event.getId())));
    }
}
