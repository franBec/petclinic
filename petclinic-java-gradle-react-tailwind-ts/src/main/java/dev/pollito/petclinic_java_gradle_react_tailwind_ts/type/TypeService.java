package dev.pollito.petclinic_java_gradle_react_tailwind_ts.type;

import java.util.List;
import java.util.Map;

public interface TypeService {

    List<Type> findAll();

    Type get(Integer id);

    Integer create(Type type);

    void update(Integer id, Type type);

    void delete(Integer id);

    Map<Integer, Integer> getTypeValues();
}
