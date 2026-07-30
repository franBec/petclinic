package dev.pollito.petclinic_java_gradle_react_tailwind_ts.vet;

import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VetDTO {

    private Integer id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private OffsetDateTime createdAt;

    @NotNull
    private OffsetDateTime updatedAt;

    private OffsetDateTime deletedAt;

    private List<Integer> vetSpecialtySpecialties;

}
