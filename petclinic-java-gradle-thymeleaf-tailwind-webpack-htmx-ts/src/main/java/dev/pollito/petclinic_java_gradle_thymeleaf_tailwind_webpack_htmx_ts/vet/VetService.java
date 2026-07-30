package dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.vet;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VetService {

    Page<VetDTO> findAll(String filter, Pageable pageable);

    VetDTO get(Integer id);

    Integer create(VetDTO vetDTO);

    void update(Integer id, VetDTO vetDTO);

    void delete(Integer id);

}
