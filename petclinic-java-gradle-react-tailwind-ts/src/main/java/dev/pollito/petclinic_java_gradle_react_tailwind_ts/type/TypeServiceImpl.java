package dev.pollito.petclinic_java_gradle_react_tailwind_ts.type;

import dev.pollito.petclinic_java_gradle_react_tailwind_ts.events.BeforeDeleteType;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.util.CustomCollectors;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.util.NotFoundException;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TypeServiceImpl implements TypeService {

    private final TypeRepository typeRepository;
    private final ApplicationEventPublisher publisher;

    @Override
    public List<Type> findAll() {
        return typeRepository.findAll(Sort.by("id"));
    }

    @Override
    public Type get(final Integer id) {
        return typeRepository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Integer create(final Type type) {
        return typeRepository.save(type).getId();
    }

    @Override
    public void update(final Integer id, final Type type) {
        type.setId(id);
        typeRepository.findById(id).orElseThrow(NotFoundException::new);
        typeRepository.save(type);
    }

    @Override
    public void delete(final Integer id) {
        final Type type = typeRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        publisher.publishEvent(new BeforeDeleteType(id));
        typeRepository.delete(type);
    }

    @Override
    public Map<Integer, Integer> getTypeValues() {
        return typeRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Type::getId, Type::getId));
    }
}
