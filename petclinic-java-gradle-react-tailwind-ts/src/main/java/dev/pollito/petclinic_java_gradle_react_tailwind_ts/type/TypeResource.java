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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TypeResource implements TypeApi {

    private final TypeService typeService;
    private final TypeMapper typeMapper;
    private final HttpServletRequest request;

    @Override
    public ResponseEntity<TypeListResponse> getAllTypes() {
        return ResponseEntity.ok(
                new TypeListResponse()
                        .instance(request.getRequestURI())
                        .status(HttpStatus.OK.value())
                        .timestamp(OffsetDateTime.now())
                        .trace("")
                        .data(typeService.findAll().stream().map(typeMapper::map).toList()));
    }

    @Override
    public ResponseEntity<TypeGetResponse> getType(final Integer id) {
        return ResponseEntity.ok(
                new TypeGetResponse()
                        .instance(request.getRequestURI())
                        .status(HttpStatus.OK.value())
                        .timestamp(OffsetDateTime.now())
                        .trace("")
                        .data(typeMapper.map(typeService.get(id))));
    }

    @Override
    public ResponseEntity<TypeCreateResponse> createType(@Valid final TypeDTO typeDTO) {
        Integer createdId = typeService.create(typeMapper.map(typeDTO));
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
        typeService.update(id, typeMapper.map(typeDTO));
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
