package dev.pollito.petclinic_java_gradle_react_tailwind_ts.visit;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VisitDTO {

    private Integer id;

    @NotNull
    private LocalDate visitDate;

    @NotNull
    private String description;

    @NotNull
    private OffsetDateTime createdAt;

    @NotNull
    private OffsetDateTime updatedAt;

    private OffsetDateTime deletedAt;

    @NotNull
    private Integer pet;

}
