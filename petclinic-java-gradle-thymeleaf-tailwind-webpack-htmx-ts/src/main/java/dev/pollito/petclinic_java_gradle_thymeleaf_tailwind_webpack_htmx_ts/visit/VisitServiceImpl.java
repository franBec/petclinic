package dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.visit;

import dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.events.BeforeDeletePet;
import dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.pet.PetRepository;
import dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.util.NotFoundException;
import dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.util.ReferencedException;
import java.util.List;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;
    private final PetRepository petRepository;
    private final VisitMapper visitMapper;

    public VisitServiceImpl(final VisitRepository visitRepository,
            final PetRepository petRepository, final VisitMapper visitMapper) {
        this.visitRepository = visitRepository;
        this.petRepository = petRepository;
        this.visitMapper = visitMapper;
    }

    @Override
    public List<VisitDTO> findAll() {
        final List<Visit> visits = visitRepository.findAll(Sort.by("id"));
        return visits.stream()
                .map(visit -> visitMapper.updateVisitDTO(visit, new VisitDTO()))
                .toList();
    }

    @Override
    public VisitDTO get(final Integer id) {
        return visitRepository.findById(id)
                .map(visit -> visitMapper.updateVisitDTO(visit, new VisitDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Integer create(final VisitDTO visitDTO) {
        final Visit visit = new Visit();
        visitMapper.updateVisit(visitDTO, visit, petRepository);
        return visitRepository.save(visit).getId();
    }

    @Override
    public void update(final Integer id, final VisitDTO visitDTO) {
        final Visit visit = visitRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        visitMapper.updateVisit(visitDTO, visit, petRepository);
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
