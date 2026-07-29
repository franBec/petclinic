package dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.specialty;

import java.util.List;
import java.util.Map;


public interface SpecialtyService {

    List<SpecialtyDTO> findAll();

    SpecialtyDTO get(Integer id);

    Integer create(SpecialtyDTO specialtyDTO);

    void update(Integer id, SpecialtyDTO specialtyDTO);

    void delete(Integer id);

    Map<Integer, Integer> getSpecialtyValues();

}
