package dev.pollito.petclinic_java_gradle_react_tailwind_ts.type;

import dev.pollito.petclinic_java_gradle_react_tailwind_ts.generated.model.TypeDTO;
import java.util.List;
import java.util.Map;

public interface TypeService {

    List<TypeDTO> findAll();

    TypeDTO get(Integer id);

    Integer create(TypeDTO typeDTO);

    void update(Integer id, TypeDTO typeDTO);

    void delete(Integer id);

    Map<Integer, Integer> getTypeValues();

}
