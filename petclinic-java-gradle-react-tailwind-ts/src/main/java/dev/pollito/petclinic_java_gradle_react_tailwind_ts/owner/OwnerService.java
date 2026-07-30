package dev.pollito.petclinic_java_gradle_react_tailwind_ts.owner;

import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OwnerService {

    Page<Owner> findAll(String filter, Pageable pageable);

    Owner get(Integer id);

    Integer create(Owner owner);

    void update(Integer id, Owner owner);

    void delete(Integer id);

    Map<Integer, Integer> getOwnerValues();
}
