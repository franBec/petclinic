package dev.pollito.petclinic_java_gradle_react_tailwind_ts.vet;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VetService {

    Page<Vet> findAll(String filter, Pageable pageable);

    Vet get(Integer id);

    Integer create(Vet vet);

    void update(Integer id, Vet vet);

    void delete(Integer id);
}
