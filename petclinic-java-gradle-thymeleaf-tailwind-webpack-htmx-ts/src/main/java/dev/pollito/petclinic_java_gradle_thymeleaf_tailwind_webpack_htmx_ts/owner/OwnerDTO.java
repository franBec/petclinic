package dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.owner;

import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;


@Getter
@Setter
public class OwnerDTO {

    private Integer id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String address;

    @NotNull
    private String city;

    @NotNull
    private String telephone;

    @NotNull
    private String email;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime createdAt;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime updatedAt;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime deletedAt;

}
