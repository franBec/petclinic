package dev.pollito.petclinic_java_gradle_react_tailwind_ts.vet;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VetRepository extends JpaRepository<Vet, Integer> {

    Page<Vet> findAllById(Integer id, Pageable pageable);

    List<Vet> findAllByVetSpecialtySpecialtiesId(Integer id);

}
