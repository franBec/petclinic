package dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.specialty;

import dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.events.BeforeDeleteSpecialty;
import dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.util.CustomCollectors;
import dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.util.NotFoundException;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class SpecialtyServiceImpl implements SpecialtyService {

    private final SpecialtyRepository specialtyRepository;
    private final ApplicationEventPublisher publisher;

    @Override
    public List<Specialty> findAll() {
        return specialtyRepository.findAll(Sort.by("id"));
    }

    @Override
    public Specialty get(final Integer id) {
        return specialtyRepository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Integer create(final Specialty specialty) {
        return specialtyRepository.save(specialty).getId();
    }

    @Override
    public void update(final Integer id, final Specialty specialty) {
        specialty.setId(id);
        specialtyRepository.findById(id).orElseThrow(NotFoundException::new);
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
