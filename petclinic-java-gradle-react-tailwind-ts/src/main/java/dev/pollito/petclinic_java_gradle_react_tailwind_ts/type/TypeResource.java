package dev.pollito.petclinic_java_gradle_react_tailwind_ts.type;

import dev.pollito.petclinic_java_gradle_react_tailwind_ts.generated.api.TypeApi;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.generated.model.TypeCreateResponse;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.generated.model.TypeDTO;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.generated.model.TypeGetResponse;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.generated.model.TypeListResponse;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.generated.model.TypeUpdateResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.time.OffsetDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TypeResource implements TypeApi {

    private final TypeService typeService;
    private final HttpServletRequest request;

    public TypeResource(final TypeService typeService, final HttpServletRequest request) {
        this.typeService = typeService;
        this.request = request;
    }

    @Override
    public ResponseEntity<TypeListResponse> getAllTypes() {
        return ResponseEntity.ok(
                new TypeListResponse()
                        .instance(request.getRequestURI())
                        .status(HttpStatus.OK.value())
                        .timestamp(OffsetDateTime.now())
                        .trace("")
                        .data(typeService.findAll()));
    }

    @Override
    public ResponseEntity<TypeGetResponse> getType(final Integer id) {
        return ResponseEntity.ok(
                new TypeGetResponse()
                        .instance(request.getRequestURI())
                        .status(HttpStatus.OK.value())
                        .timestamp(OffsetDateTime.now())
                        .trace("")
                        .data(typeService.get(id)));
    }

    @Override
    public ResponseEntity<TypeCreateResponse> createType(@Valid final TypeDTO typeDTO) {
        Integer createdId = typeService.create(typeDTO);
        return new ResponseEntity<>(
                new TypeCreateResponse()
                        .instance(request.getRequestURI())
                        .status(HttpStatus.CREATED.value())
                        .timestamp(OffsetDateTime.now())
                        .trace("")
                        .data(createdId),
                HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<TypeUpdateResponse> updateType(
            final Integer id, @Valid final TypeDTO typeDTO) {
        typeService.update(id, typeDTO);
        return ResponseEntity.ok(
                new TypeUpdateResponse()
                        .instance(request.getRequestURI())
                        .status(HttpStatus.OK.value())
                        .timestamp(OffsetDateTime.now())
                        .trace("")
                        .data(id));
    }

    @Override
    public ResponseEntity<Void> deleteType(final Integer id) {
        typeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
