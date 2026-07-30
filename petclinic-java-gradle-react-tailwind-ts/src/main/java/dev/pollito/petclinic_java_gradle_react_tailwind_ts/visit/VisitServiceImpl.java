package dev.pollito.petclinic_java_gradle_react_tailwind_ts.visit;

import dev.pollito.petclinic_java_gradle_react_tailwind_ts.events.BeforeDeletePet;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.util.NotFoundException;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.util.ReferencedException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;

    @Override
    public List<Visit> findAll() {
        return visitRepository.findAll(Sort.by("id"));
    }

    @Override
    public Visit get(final Integer id) {
        return visitRepository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Integer create(final Visit visit) {
        return visitRepository.save(visit).getId();
    }

    @Override
    public void update(final Integer id, final Visit visit) {
        visit.setId(id);
        visitRepository.findById(id).orElseThrow(NotFoundException::new);
        visitRepository.save(visit);
    }

    @Override
    public void delete(final Integer id) {
        final Visit visit = visitRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        visitRepository.delete(visit);
    }

    @EventListener(BeforeDeletePet.class)
    public void on(final BeforeDeletePet event) {
        final ReferencedException referencedException = new ReferencedException();
        final Visit petVisit = visitRepository.findFirstByPetId(event.getId());
        if (petVisit != null) {
            referencedException.setKey("pet.visit.pet.referenced");
            referencedException.addParam(petVisit.getId());
            throw referencedException;
        }
    }
}
