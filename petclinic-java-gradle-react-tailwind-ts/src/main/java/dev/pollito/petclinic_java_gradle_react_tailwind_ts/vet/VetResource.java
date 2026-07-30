package dev.pollito.petclinic_java_gradle_react_tailwind_ts.vet;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/vets", produces = MediaType.APPLICATION_JSON_VALUE)
public class VetResource {

    private final VetService vetService;

    public VetResource(final VetService vetService) {
        this.vetService = vetService;
    }

    @Operation(parameters = {
            @Parameter(name = "page", in = ParameterIn.QUERY, schema = @Schema(implementation = Integer.class)),
            @Parameter(name = "size", in = ParameterIn.QUERY, schema = @Schema(implementation = Integer.class)),
            @Parameter(name = "sort", in = ParameterIn.QUERY, schema = @Schema(implementation = String.class))
    })
    @GetMapping
    public ResponseEntity<Page<VetDTO>> getAllVets(
            @RequestParam(name = "filter", required = false) final String filter,
            @Parameter(hidden = true) @SortDefault(sort = "id") @PageableDefault(size = 20) final Pageable pageable) {
        return ResponseEntity.ok(vetService.findAll(filter, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VetDTO> getVet(@PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(vetService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createVet(@RequestBody @Valid final VetDTO vetDTO) {
        final Integer createdId = vetService.create(vetDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateVet(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final VetDTO vetDTO) {
        vetService.update(id, vetDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteVet(@PathVariable(name = "id") final Integer id) {
        vetService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
