package dev.pollito.petclinic_java_gradle_react_tailwind_ts.type;

import dev.pollito.petclinic_java_gradle_react_tailwind_ts.events.BeforeDeleteType;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.util.CustomCollectors;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.util.NotFoundException;
import java.util.List;
import java.util.Map;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class TypeServiceImpl implements TypeService {

    private final TypeRepository typeRepository;
    private final ApplicationEventPublisher publisher;
    private final TypeMapper typeMapper;

    public TypeServiceImpl(final TypeRepository typeRepository,
            final ApplicationEventPublisher publisher, final TypeMapper typeMapper) {
        this.typeRepository = typeRepository;
        this.publisher = publisher;
        this.typeMapper = typeMapper;
    }

    @Override
    public List<TypeDTO> findAll() {
        final List<Type> types = typeRepository.findAll(Sort.by("id"));
        return types.stream()
                .map(type -> typeMapper.updateTypeDTO(type, new TypeDTO()))
                .toList();
    }

    @Override
    public TypeDTO get(final Integer id) {
        return typeRepository.findById(id)
                .map(type -> typeMapper.updateTypeDTO(type, new TypeDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Integer create(final TypeDTO typeDTO) {
        final Type type = new Type();
        typeMapper.updateType(typeDTO, type);
        return typeRepository.save(type).getId();
    }

    @Override
    public void update(final Integer id, final TypeDTO typeDTO) {
        final Type type = typeRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        typeMapper.updateType(typeDTO, type);
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
