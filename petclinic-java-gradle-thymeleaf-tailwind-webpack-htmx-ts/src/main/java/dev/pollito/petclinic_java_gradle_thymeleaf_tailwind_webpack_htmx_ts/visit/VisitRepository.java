package dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.visit;

import org.springframework.data.jpa.repository.JpaRepository;


public interface VisitRepository extends JpaRepository<Visit, Integer> {

    Visit findFirstByPetId(Integer id);

}
