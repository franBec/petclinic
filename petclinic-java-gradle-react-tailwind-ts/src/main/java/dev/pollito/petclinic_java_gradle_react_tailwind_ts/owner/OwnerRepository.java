package dev.pollito.petclinic_java_gradle_react_tailwind_ts.owner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, Integer> {

    Page<Owner> findAllById(Integer id, Pageable pageable);

}
