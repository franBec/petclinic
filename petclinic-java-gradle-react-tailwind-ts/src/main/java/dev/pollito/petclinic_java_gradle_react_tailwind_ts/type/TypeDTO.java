package dev.pollito.petclinic_java_gradle_react_tailwind_ts.type;

import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TypeDTO {

    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private OffsetDateTime createdAt;

    @NotNull
    private OffsetDateTime updatedAt;

    private OffsetDateTime deletedAt;

}
