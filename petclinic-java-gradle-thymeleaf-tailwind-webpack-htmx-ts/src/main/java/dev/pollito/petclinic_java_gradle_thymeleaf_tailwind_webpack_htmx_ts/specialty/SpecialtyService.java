package dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.specialty;

import java.util.List;
import java.util.Map;

public interface SpecialtyService {

    List<Specialty> findAll();

    Specialty get(Integer id);

    Integer create(Specialty specialty);

    void update(Integer id, Specialty specialty);

    void delete(Integer id);

    Map<Integer, Integer> getSpecialtyValues();
}
