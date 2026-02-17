package io.github.dan7arievlis.autoflextest.controller;

import io.github.dan7arievlis.autoflextest.controller.dto.imputProduct.InputProductResponseDTO;
import io.github.dan7arievlis.autoflextest.controller.dto.input.InputResponseDTO;
import io.github.dan7arievlis.autoflextest.controller.dto.mapper.InputMapper;
import io.github.dan7arievlis.autoflextest.controller.dto.mapper.InputProductMapper;
import io.github.dan7arievlis.autoflextest.controller.dto.mapper.ProductMapper;
import io.github.dan7arievlis.autoflextest.controller.dto.product.ProductRequestDTO;
import io.github.dan7arievlis.autoflextest.controller.dto.product.ProductResponseDTO;
import io.github.dan7arievlis.autoflextest.model.InputProduct;
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
@RequestMapping("products")
@RequiredArgsConstructor
public class ProductController implements GenericController {

    private final ProductService service;
    private final InputService inputService;
    private final ProductMapper mapper;
    private final InputProductMapper inputProductMapper;

    // create POST /
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> save(@RequestBody ProductRequestDTO dto) {
        var product = mapper.requestToEntity(dto);
        service.create(product, dto);
        URI location = generateHeaderLocation(product.getId());
        return ResponseEntity.created(location).build();
    }

    // details GET /id
    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable String id) {
        return service.findById(UUID.fromString(id))
                .map(product -> ResponseEntity
                        .ok(mapper.entityToResponse(product, service.calculateMaxProduction(product), service.calculateTotalValue(product)))
                )
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
    }

    // search GET /
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<Page<ProductResponseDTO>> search(
            @RequestParam(name = "name", required = false)
            String name,
            @RequestParam(value = "page", defaultValue = "0")
            Integer page,
            @RequestParam(value = "page-size", defaultValue = "10")
            Integer pageSize
    ) {
        Page<ProductResponseDTO> result = service.search(name, page, pageSize);
        return ResponseEntity.ok(result);
    }

    // update PUT /id
    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?>update(@PathVariable String id, @RequestBody @Valid ProductRequestDTO request) {
        return service.findById(UUID.fromString(id))
                .map(product -> {
                    service.update(product, request);

                    return ResponseEntity.noContent().build();
                })
                .orElseThrow(() -> new EntityNotFoundException("Product not found."));
    }

    // delete DELETE /id
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable String id) {
        return service.findById(UUID.fromString(id))
                .map(product -> {
                    service.delete(product);
                    return ResponseEntity.noContent().build();
                })
                .orElseThrow( () -> new EntityNotFoundException("Product not found."));
    }

    // find inputs GET /id/inputs
    @GetMapping("{id}/inputs")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<List<InputProductResponseDTO>> searchInputs(@PathVariable String id) {
        UUID uuid = UUID.fromString(id);
        List<InputProductResponseDTO> result = inputService.searchInputsById(uuid)
                .stream().map(inputProductMapper::entityToResponse)
                .toList();
        return ResponseEntity.ok(result);
    }
}
