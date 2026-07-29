package dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.pet;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;


@Getter
@Setter
public class PetDTO {

    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private LocalDate birthDate;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime createdAt;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime updatedAt;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime deletedAt;

    @NotNull
    private Integer type;

    @NotNull
    private Integer owner;

}
