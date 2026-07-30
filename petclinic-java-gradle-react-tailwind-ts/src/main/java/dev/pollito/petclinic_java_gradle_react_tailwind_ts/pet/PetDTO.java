package dev.pollito.petclinic_java_gradle_react_tailwind_ts.pet;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetDTO {

    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private LocalDate birthDate;

    @NotNull
    private OffsetDateTime createdAt;

    @NotNull
    private OffsetDateTime updatedAt;

    private OffsetDateTime deletedAt;

    @NotNull
    private Integer type;

    @NotNull
    private Integer owner;

}
