package dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.vet;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VetService {

    Page<Vet> findAll(String filter, Pageable pageable);

    Vet get(Integer id);

    Integer create(Vet vet);

    void update(Integer id, Vet vet);

    void delete(Integer id);
}
