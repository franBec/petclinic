package dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.pet;

import java.util.List;
import java.util.Map;

public interface PetService {

    List<PetDTO> findAll();

    PetDTO get(Integer id);

    Integer create(PetDTO petDTO);

    void update(Integer id, PetDTO petDTO);

    void delete(Integer id);

    Map<Integer, Integer> getPetValues();

}
