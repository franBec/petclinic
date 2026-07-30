package dev.pollito.petclinic_java_gradle_react_tailwind_ts.specialty;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/specialties", produces = MediaType.APPLICATION_JSON_VALUE)
public class SpecialtyResource {

    private final SpecialtyService specialtyService;

    public SpecialtyResource(final SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    @GetMapping
    public ResponseEntity<List<SpecialtyDTO>> getAllSpecialties() {
        return ResponseEntity.ok(specialtyService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecialtyDTO> getSpecialty(@PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(specialtyService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createSpecialty(
            @RequestBody @Valid final SpecialtyDTO specialtyDTO) {
        final Integer createdId = specialtyService.create(specialtyDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateSpecialty(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final SpecialtyDTO specialtyDTO) {
        specialtyService.update(id, specialtyDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteSpecialty(@PathVariable(name = "id") final Integer id) {
        specialtyService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
