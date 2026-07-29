package dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PaginationStep {

    private boolean active = false;
    private boolean disabled = false;
    private String label;
    private String url;

}
