package dev.pollito.petclinic_java_gradle_react_tailwind_ts.visit;

import dev.pollito.petclinic_java_gradle_react_tailwind_ts.generated.model.VisitDTO;
import java.util.List;

public interface VisitService {

    List<VisitDTO> findAll();

    VisitDTO get(Integer id);

    Integer create(VisitDTO visitDTO);

    void update(Integer id, VisitDTO visitDTO);

    void delete(Integer id);

}
