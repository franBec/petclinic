package dev.pollito.petclinic_java_gradle_react_tailwind_ts.visit;

import dev.pollito.petclinic_java_gradle_react_tailwind_ts.generated.api.VisitApi;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.generated.model.VisitCreateResponse;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.generated.model.VisitDTO;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.generated.model.VisitGetResponse;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.generated.model.VisitListResponse;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.generated.model.VisitUpdateResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.time.OffsetDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VisitResource implements VisitApi {

    private final VisitService visitService;
    private final HttpServletRequest request;

    public VisitResource(final VisitService visitService, final HttpServletRequest request) {
        this.visitService = visitService;
        this.request = request;
    }

    @Override
    public ResponseEntity<VisitListResponse> getAllVisits() {
        return ResponseEntity.ok(
                new VisitListResponse()
                        .instance(request.getRequestURI())
                        .status(HttpStatus.OK.value())
                        .timestamp(OffsetDateTime.now())
                        .trace("")
                        .data(visitService.findAll()));
    }

    @Override
    public ResponseEntity<VisitGetResponse> getVisit(final Integer id) {
        return ResponseEntity.ok(
                new VisitGetResponse()
                        .instance(request.getRequestURI())
                        .status(HttpStatus.OK.value())
                        .timestamp(OffsetDateTime.now())
                        .trace("")
                        .data(visitService.get(id)));
    }

    @Override
    public ResponseEntity<VisitCreateResponse> createVisit(@Valid final VisitDTO visitDTO) {
        Integer createdId = visitService.create(visitDTO);
        return new ResponseEntity<>(
                new VisitCreateResponse()
                        .instance(request.getRequestURI())
                        .status(HttpStatus.CREATED.value())
                        .timestamp(OffsetDateTime.now())
                        .trace("")
                        .data(createdId),
                HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<VisitUpdateResponse> updateVisit(
            final Integer id, @Valid final VisitDTO visitDTO) {
        visitService.update(id, visitDTO);
        return ResponseEntity.ok(
                new VisitUpdateResponse()
                        .instance(request.getRequestURI())
                        .status(HttpStatus.OK.value())
                        .timestamp(OffsetDateTime.now())
                        .trace("")
                        .data(id));
    }

    @Override
    public ResponseEntity<Void> deleteVisit(final Integer id) {
        visitService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
