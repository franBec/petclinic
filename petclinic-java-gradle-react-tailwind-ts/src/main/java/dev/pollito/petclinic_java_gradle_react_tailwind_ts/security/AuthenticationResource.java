package dev.pollito.petclinic_java_gradle_react_tailwind_ts.security;

import dev.pollito.petclinic_java_gradle_react_tailwind_ts.generated.api.AuthenticationApi;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.generated.model.AuthenticationGetResponse;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.generated.model.AuthenticationRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.time.OffsetDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class AuthenticationResource implements AuthenticationApi {

    private final AuthenticationManager authenticationManager;
    private final AdminUserDetailsService adminUserDetailsService;
    private final AdminTokenService adminTokenService;
    private final HttpServletRequest request;

    public AuthenticationResource(
            final AuthenticationManager authenticationManager,
            final AdminUserDetailsService adminUserDetailsService,
            final AdminTokenService adminTokenService,
            final HttpServletRequest request) {
        this.authenticationManager = authenticationManager;
        this.adminUserDetailsService = adminUserDetailsService;
        this.adminTokenService = adminTokenService;
        this.request = request;
    }

    @Override
    public ResponseEntity<AuthenticationGetResponse> authenticate(
            @Valid final AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (final BadCredentialsException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(
                new AuthenticationGetResponse()
                        .instance(request.getRequestURI())
                        .status(HttpStatus.OK.value())
                        .timestamp(OffsetDateTime.now())
                        .trace("")
                        .data(adminTokenService.generateToken(
                                adminUserDetailsService.loadUserByUsername(authenticationRequest.getUsername()))));
    }
}
