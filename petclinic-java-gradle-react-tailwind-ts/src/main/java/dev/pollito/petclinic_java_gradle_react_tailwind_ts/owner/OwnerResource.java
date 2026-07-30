package dev.pollito.petclinic_java_gradle_react_tailwind_ts.owner;

import dev.pollito.petclinic_java_gradle_react_tailwind_ts.generated.api.OwnerApi;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.generated.model.OwnerCreateResponse;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.generated.model.OwnerDTO;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.generated.model.OwnerGetResponse;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.generated.model.OwnerListData;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.generated.model.OwnerListResponse;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.generated.model.OwnerUpdateResponse;
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
public class OwnerResource implements OwnerApi {

    private final OwnerService ownerService;
    private final HttpServletRequest request;

    public OwnerResource(final OwnerService ownerService, final HttpServletRequest request) {
        this.ownerService = ownerService;
        this.request = request;
    }

    @Override
    public ResponseEntity<OwnerListResponse> getAllOwners(
            final String filter,
            @SortDefault(sort = "id") @PageableDefault(size = 20) final Pageable pageable) {
        Page<OwnerDTO> page = ownerService.findAll(filter, pageable);
        return ResponseEntity.ok(
                new OwnerListResponse()
                        .instance(request.getRequestURI())
                        .status(HttpStatus.OK.value())
                        .timestamp(OffsetDateTime.now())
                        .trace("")
                        .data(new OwnerListData()
                                .content(page.getContent())
                                .page(page.getNumber())
                                .size(page.getSize())
                                .totalElements(page.getTotalElements())
                                .totalPages(page.getTotalPages())));
    }

    @Override
    public ResponseEntity<OwnerGetResponse> getOwner(final Integer id) {
        return ResponseEntity.ok(
                new OwnerGetResponse()
                        .instance(request.getRequestURI())
                        .status(HttpStatus.OK.value())
                        .timestamp(OffsetDateTime.now())
                        .trace("")
                        .data(ownerService.get(id)));
    }

    @Override
    public ResponseEntity<OwnerCreateResponse> createOwner(@Valid final OwnerDTO ownerDTO) {
        Integer createdId = ownerService.create(ownerDTO);
        return new ResponseEntity<>(
                new OwnerCreateResponse()
                        .instance(request.getRequestURI())
                        .status(HttpStatus.CREATED.value())
                        .timestamp(OffsetDateTime.now())
                        .trace("")
                        .data(createdId),
                HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<OwnerUpdateResponse> updateOwner(
            final Integer id, @Valid final OwnerDTO ownerDTO) {
        ownerService.update(id, ownerDTO);
        return ResponseEntity.ok(
                new OwnerUpdateResponse()
                        .instance(request.getRequestURI())
                        .status(HttpStatus.OK.value())
                        .timestamp(OffsetDateTime.now())
                        .trace("")
                        .data(id));
    }

    @Override
    public ResponseEntity<Void> deleteOwner(final Integer id) {
        ownerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
