package dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationModel {

    private List<PaginationStep> steps;
    private String elements;

}
