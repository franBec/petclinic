package dev.pollito.petclinic_java_gradle_react_tailwind_ts.vet;

import dev.pollito.petclinic_java_gradle_react_tailwind_ts.events.BeforeDeleteSpecialty;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.generated.model.VetDTO;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.specialty.SpecialtyRepository;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.util.NotFoundException;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class VetServiceImpl implements VetService {

    private final VetRepository vetRepository;
    private final SpecialtyRepository specialtyRepository;
    private final VetMapper vetMapper;

    public VetServiceImpl(final VetRepository vetRepository,
            final SpecialtyRepository specialtyRepository, final VetMapper vetMapper) {
        this.vetRepository = vetRepository;
        this.specialtyRepository = specialtyRepository;
        this.vetMapper = vetMapper;
    }

    @Override
    public Page<VetDTO> findAll(final String filter, final Pageable pageable) {
        Page<Vet> page;
        if (filter != null) {
            Integer integerFilter = null;
            try {
                integerFilter = Integer.parseInt(filter);
            } catch (final NumberFormatException numberFormatException) {
                // keep null - no parseable input
            }
            page = vetRepository.findAllById(integerFilter, pageable);
        } else {
            page = vetRepository.findAll(pageable);
        }
        return new PageImpl<>(page.getContent()
                .stream()
                .map(vet -> vetMapper.updateVetDTO(vet, new VetDTO()))
                .toList(),
                pageable, page.getTotalElements());
    }

    @Override
    public VetDTO get(final Integer id) {
        return vetRepository.findById(id)
                .map(vet -> vetMapper.updateVetDTO(vet, new VetDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Integer create(final VetDTO vetDTO) {
        final Vet vet = new Vet();
        vetMapper.updateVet(vetDTO, vet, specialtyRepository);
        return vetRepository.save(vet).getId();
    }

    @Override
    public void update(final Integer id, final VetDTO vetDTO) {
        final Vet vet = vetRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        vetMapper.updateVet(vetDTO, vet, specialtyRepository);
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
        // remove many-to-many relations at owning side
        vetRepository.findAllByVetSpecialtySpecialtiesId(event.getId()).forEach(
                vet -> vet.getVetSpecialtySpecialties().removeIf(specialty -> specialty.getId().equals(event.getId())));
    }

}
