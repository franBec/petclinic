package dev.pollito.petclinic_java_gradle_react_tailwind_ts.pet;

import dev.pollito.petclinic_java_gradle_react_tailwind_ts.generated.api.PetApi;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.generated.model.PetCreateResponse;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.generated.model.PetDTO;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.generated.model.PetGetResponse;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.generated.model.PetListResponse;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.generated.model.PetUpdateResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PetResource implements PetApi {

    private final PetService petService;
    private final PetMapper petMapper;
    private final HttpServletRequest request;

    @Override
    public ResponseEntity<PetListResponse> getAllPets() {
        return ResponseEntity.ok(
                new PetListResponse()
                        .instance(request.getRequestURI())
                        .status(HttpStatus.OK.value())
                        .timestamp(OffsetDateTime.now())
                        .trace("")
                        .data(petService.findAll().stream().map(petMapper::map).toList()));
    }

    @Override
    public ResponseEntity<PetGetResponse> getPet(final Integer id) {
        return ResponseEntity.ok(
                new PetGetResponse()
                        .instance(request.getRequestURI())
                        .status(HttpStatus.OK.value())
                        .timestamp(OffsetDateTime.now())
                        .trace("")
                        .data(petMapper.map(petService.get(id))));
    }

    @Override
    public ResponseEntity<PetCreateResponse> createPet(@Valid final PetDTO petDTO) {
        Integer createdId = petService.create(petMapper.map(petDTO));
        return new ResponseEntity<>(
                new PetCreateResponse()
                        .instance(request.getRequestURI())
                        .status(HttpStatus.CREATED.value())
                        .timestamp(OffsetDateTime.now())
                        .trace("")
                        .data(createdId),
                HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<PetUpdateResponse> updatePet(
            final Integer id, @Valid final PetDTO petDTO) {
        petService.update(id, petMapper.map(petDTO));
        return ResponseEntity.ok(
                new PetUpdateResponse()
                        .instance(request.getRequestURI())
                        .status(HttpStatus.OK.value())
                        .timestamp(OffsetDateTime.now())
                        .trace("")
                        .data(id));
    }

    @Override
    public ResponseEntity<Void> deletePet(final Integer id) {
        petService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
