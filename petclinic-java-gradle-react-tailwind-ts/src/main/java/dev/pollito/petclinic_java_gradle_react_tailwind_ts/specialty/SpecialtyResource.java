package dev.pollito.petclinic_java_gradle_react_tailwind_ts.specialty;

import dev.pollito.petclinic_java_gradle_react_tailwind_ts.generated.api.SpecialtyApi;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.generated.model.SpecialtyCreateResponse;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.generated.model.SpecialtyDTO;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.generated.model.SpecialtyGetResponse;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.generated.model.SpecialtyListResponse;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.generated.model.SpecialtyUpdateResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SpecialtyResource implements SpecialtyApi {

    private final SpecialtyService specialtyService;
    private final SpecialtyMapper specialtyMapper;
    private final HttpServletRequest request;

    @Override
    public ResponseEntity<SpecialtyListResponse> getAllSpecialties() {
        return ResponseEntity.ok(
                new SpecialtyListResponse()
                        .instance(request.getRequestURI())
                        .status(HttpStatus.OK.value())
                        .timestamp(OffsetDateTime.now())
                        .trace("")
                        .data(specialtyService.findAll().stream().map(specialtyMapper::map).toList()));
    }

    @Override
    public ResponseEntity<SpecialtyGetResponse> getSpecialty(final Integer id) {
        return ResponseEntity.ok(
                new SpecialtyGetResponse()
                        .instance(request.getRequestURI())
                        .status(HttpStatus.OK.value())
                        .timestamp(OffsetDateTime.now())
                        .trace("")
                        .data(specialtyMapper.map(specialtyService.get(id))));
    }

    @Override
    public ResponseEntity<SpecialtyCreateResponse> createSpecialty(
            @Valid final SpecialtyDTO specialtyDTO) {
        Integer createdId = specialtyService.create(specialtyMapper.map(specialtyDTO));
        return new ResponseEntity<>(
                new SpecialtyCreateResponse()
                        .instance(request.getRequestURI())
                        .status(HttpStatus.CREATED.value())
                        .timestamp(OffsetDateTime.now())
                        .trace("")
                        .data(createdId),
                HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<SpecialtyUpdateResponse> updateSpecialty(
            final Integer id, @Valid final SpecialtyDTO specialtyDTO) {
        specialtyService.update(id, specialtyMapper.map(specialtyDTO));
        return ResponseEntity.ok(
                new SpecialtyUpdateResponse()
                        .instance(request.getRequestURI())
                        .status(HttpStatus.OK.value())
                        .timestamp(OffsetDateTime.now())
                        .trace("")
                        .data(id));
    }

    @Override
    public ResponseEntity<Void> deleteSpecialty(final Integer id) {
        specialtyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
