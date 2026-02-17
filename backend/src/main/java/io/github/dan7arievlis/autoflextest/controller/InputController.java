package io.github.dan7arievlis.autoflextest.controller;

import io.github.dan7arievlis.autoflextest.controller.dto.input.InputCreateRequestDTO;
import io.github.dan7arievlis.autoflextest.controller.dto.input.InputResponseDTO;
import io.github.dan7arievlis.autoflextest.controller.dto.input.InputUpdateRequestDTO;
import io.github.dan7arievlis.autoflextest.controller.dto.mapper.InputMapper;
import io.github.dan7arievlis.autoflextest.controller.dto.mapper.ProductMapper;
import io.github.dan7arievlis.autoflextest.controller.dto.product.ProductResponseDTO;
import io.github.dan7arievlis.autoflextest.service.InputService;
import io.github.dan7arievlis.autoflextest.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("inputs")
@RequiredArgsConstructor
public class InputController implements GenericController {

    private final InputService service;
    private final ProductService productService;
    private final InputMapper mapper;
    private final ProductMapper productMapper;

    // create POST /
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> save(@RequestBody @Valid InputCreateRequestDTO dto) {
        var input = mapper.requestToEntity(dto);
        service.create(input);
        URI location = generateHeaderLocation(input.getId());
        return ResponseEntity.created(location).build();
    }

    // details GET /id
    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<InputResponseDTO> findById(@PathVariable String id) {
        return service.findById(UUID.fromString(id))
                .map(input -> ResponseEntity.ok(mapper.entityToResponse(input)))
                .orElseThrow(() -> new EntityNotFoundException("Input not found with id: " + id));
    }

    // search GET /
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<Page<InputResponseDTO>> search(
            @RequestParam(name = "name", required = false)
            String name,
            @RequestParam(value = "page", defaultValue = "0")
            Integer page,
            @RequestParam(value = "page-size", defaultValue = "10")
            Integer pageSize
    ) {
        Page<InputResponseDTO> result = service
                .search(name, page, pageSize)
                .map(mapper::entityToResponse);

        return ResponseEntity.ok(result);
    }

    // update PUT /id
    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?>update(@PathVariable String id, @RequestBody @Valid InputUpdateRequestDTO request) {
        return service.findById(UUID.fromString(id))
                .map(input -> {
                    service.update(input, request);

                    return ResponseEntity.noContent().build();
                })
                .orElseThrow(() -> new EntityNotFoundException("Input not found."));
    }

    // delete DELETE /id
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable String id) {
        return service.findById(UUID.fromString(id))
                .map(input -> {
                    service.delete(input);
                    return ResponseEntity.noContent().build();
                })
                .orElseThrow( () -> new EntityNotFoundException("Input not found."));
    }

    // find products GET /id/products
    @GetMapping("{id}/products")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<List<ProductResponseDTO>> searchProducts(@PathVariable String id) {
        UUID uuid = UUID.fromString(id);
        List<ProductResponseDTO> result = productService.searchProductsById(uuid)
                .stream().map(productMapper::entityToResponse)
                .toList();
        return ResponseEntity.ok(result);
    }
}
