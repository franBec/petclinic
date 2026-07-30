package dev.pollito.petclinic_java_gradle_react_tailwind_ts.visit;

import java.util.List;

public interface VisitService {

    List<Visit> findAll();

    Visit get(Integer id);

    Integer create(Visit visit);

    void update(Integer id, Visit visit);

    void delete(Integer id);
}
