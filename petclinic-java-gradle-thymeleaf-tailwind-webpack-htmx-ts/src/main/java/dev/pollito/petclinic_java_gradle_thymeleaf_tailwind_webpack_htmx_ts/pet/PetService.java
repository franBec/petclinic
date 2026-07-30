package dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.pet;

import java.util.List;
import java.util.Map;

public interface PetService {

    List<Pet> findAll();

    Pet get(Integer id);

    Integer create(Pet pet);

    void update(Integer id, Pet pet);

    void delete(Integer id);

    Map<Integer, Integer> getPetValues();
}
