package dev.pollito.petclinic_java_gradle_react_tailwind_ts.pet;

import org.springframework.data.jpa.repository.JpaRepository;


public interface PetRepository extends JpaRepository<Pet, Integer> {

    Pet findFirstByTypeId(Integer id);

    Pet findFirstByOwnerId(Integer id);

}
