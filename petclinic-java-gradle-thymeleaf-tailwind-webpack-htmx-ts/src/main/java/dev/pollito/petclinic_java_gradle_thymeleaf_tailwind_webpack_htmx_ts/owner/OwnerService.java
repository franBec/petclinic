package dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.owner;

import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface OwnerService {

    Page<OwnerDTO> findAll(String filter, Pageable pageable);

    OwnerDTO get(Integer id);

    Integer create(OwnerDTO ownerDTO);

    void update(Integer id, OwnerDTO ownerDTO);

    void delete(Integer id);

    Map<Integer, Integer> getOwnerValues();

}
