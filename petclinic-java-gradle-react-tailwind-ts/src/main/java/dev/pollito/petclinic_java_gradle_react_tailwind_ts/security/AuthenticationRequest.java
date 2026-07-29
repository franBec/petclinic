package dev.pollito.petclinic_java_gradle_react_tailwind_ts.security;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AuthenticationRequest {

    @NotNull
    @Size(max = 20)
    private String username;

    @NotNull
    @Size(max = 72)
    private String password;

}
