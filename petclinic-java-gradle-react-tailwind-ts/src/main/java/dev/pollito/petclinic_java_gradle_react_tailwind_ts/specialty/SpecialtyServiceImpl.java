package dev.pollito.petclinic_java_gradle_react_tailwind_ts.specialty;

import dev.pollito.petclinic_java_gradle_react_tailwind_ts.events.BeforeDeleteSpecialty;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.util.CustomCollectors;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.util.NotFoundException;
import java.util.List;
import java.util.Map;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class SpecialtyServiceImpl implements SpecialtyService {

    private final SpecialtyRepository specialtyRepository;
    private final ApplicationEventPublisher publisher;
    private final SpecialtyMapper specialtyMapper;

    public SpecialtyServiceImpl(final SpecialtyRepository specialtyRepository,
            final ApplicationEventPublisher publisher, final SpecialtyMapper specialtyMapper) {
        this.specialtyRepository = specialtyRepository;
        this.publisher = publisher;
        this.specialtyMapper = specialtyMapper;
    }

    @Override
    public List<SpecialtyDTO> findAll() {
        final List<Specialty> specialties = specialtyRepository.findAll(Sort.by("id"));
        return specialties.stream()
                .map(specialty -> specialtyMapper.updateSpecialtyDTO(specialty, new SpecialtyDTO()))
                .toList();
    }

    @Override
    public SpecialtyDTO get(final Integer id) {
        return specialtyRepository.findById(id)
                .map(specialty -> specialtyMapper.updateSpecialtyDTO(specialty, new SpecialtyDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Integer create(final SpecialtyDTO specialtyDTO) {
        final Specialty specialty = new Specialty();
        specialtyMapper.updateSpecialty(specialtyDTO, specialty);
        return specialtyRepository.save(specialty).getId();
    }

    @Override
    public void update(final Integer id, final SpecialtyDTO specialtyDTO) {
        final Specialty specialty = specialtyRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        specialtyMapper.updateSpecialty(specialtyDTO, specialty);
        specialtyRepository.save(specialty);
    }

    @Override
    public void delete(final Integer id) {
        final Specialty specialty = specialtyRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        publisher.publishEvent(new BeforeDeleteSpecialty(id));
        specialtyRepository.delete(specialty);
    }

    @Override
    public Map<Integer, Integer> getSpecialtyValues() {
        return specialtyRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Specialty::getId, Specialty::getId));
    }

}
