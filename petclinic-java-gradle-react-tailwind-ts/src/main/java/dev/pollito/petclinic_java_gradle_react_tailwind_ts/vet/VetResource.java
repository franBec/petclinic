package dev.pollito.petclinic_java_gradle_react_tailwind_ts.vet;

import dev.pollito.petclinic_java_gradle_react_tailwind_ts.generated.api.VetApi;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.generated.model.VetCreateResponse;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.generated.model.VetDTO;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.generated.model.VetGetResponse;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.generated.model.VetListData;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.generated.model.VetListResponse;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.generated.model.VetUpdateResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.time.OffsetDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VetResource implements VetApi {

    private final VetService vetService;
    private final HttpServletRequest request;

    public VetResource(final VetService vetService, final HttpServletRequest request) {
        this.vetService = vetService;
        this.request = request;
    }

    @Override
    public ResponseEntity<VetListResponse> getAllVets(
            final String filter,
            @SortDefault(sort = "id") @PageableDefault(size = 20) final Pageable pageable) {
        Page<VetDTO> page = vetService.findAll(filter, pageable);
        VetListData data = new VetListData()
                .content(page.getContent())
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages());
        return ResponseEntity.ok(
                new VetListResponse()
                        .instance(request.getRequestURI())
                        .status(HttpStatus.OK.value())
                        .timestamp(OffsetDateTime.now())
                        .trace("")
                        .data(data));
    }

    @Override
    public ResponseEntity<VetGetResponse> getVet(final Integer id) {
        return ResponseEntity.ok(
                new VetGetResponse()
                        .instance(request.getRequestURI())
                        .status(HttpStatus.OK.value())
                        .timestamp(OffsetDateTime.now())
                        .trace("")
                        .data(vetService.get(id)));
    }

    @Override
    public ResponseEntity<VetCreateResponse> createVet(@Valid final VetDTO vetDTO) {
        Integer createdId = vetService.create(vetDTO);
        return new ResponseEntity<>(
                new VetCreateResponse()
                        .instance(request.getRequestURI())
                        .status(HttpStatus.CREATED.value())
                        .timestamp(OffsetDateTime.now())
                        .trace("")
                        .data(createdId),
                HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<VetUpdateResponse> updateVet(
            final Integer id, @Valid final VetDTO vetDTO) {
        vetService.update(id, vetDTO);
        return ResponseEntity.ok(
                new VetUpdateResponse()
                        .instance(request.getRequestURI())
                        .status(HttpStatus.OK.value())
                        .timestamp(OffsetDateTime.now())
                        .trace("")
                        .data(id));
    }

    @Override
    public ResponseEntity<Void> deleteVet(final Integer id) {
        vetService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
