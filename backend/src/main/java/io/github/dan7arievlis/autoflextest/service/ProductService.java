package io.github.dan7arievlis.autoflextest.service;

import io.github.dan7arievlis.autoflextest.controller.dto.input.RequiredInputRequestDTO;
import io.github.dan7arievlis.autoflextest.controller.dto.mapper.ProductMapper;
import io.github.dan7arievlis.autoflextest.controller.dto.product.ProductRequestDTO;
import io.github.dan7arievlis.autoflextest.controller.dto.product.ProductResponseDTO;
import io.github.dan7arievlis.autoflextest.model.Input;
import io.github.dan7arievlis.autoflextest.model.InputProduct;
import io.github.dan7arievlis.autoflextest.model.Product;
import io.github.dan7arievlis.autoflextest.model.embedded.InputProductId;
import io.github.dan7arievlis.autoflextest.repository.InputRepository;
import io.github.dan7arievlis.autoflextest.repository.ProductRepository;
import io.github.dan7arievlis.autoflextest.repository.specs.ProductSpecs;
import io.github.dan7arievlis.autoflextest.security.SecurityService;
import io.github.dan7arievlis.autoflextest.service.component.OptionalSpecificationSearch;
import io.github.dan7arievlis.autoflextest.validator.ProductValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ProductService implements OptionalSpecificationSearch {

    private final ProductRepository repository;
    private final InputRepository inputRepository;
    private final ProductValidator validator;
    private final ProductMapper mapper;
    private final SecurityService securityService;

    @Transactional
    public void save(Product product) {
        validator.validate(product);
        product.setLastModifiedBy(securityService.getLoggedUser());
        repository.save(product);
    }

    @Transactional
    public void create(Product product, ProductRequestDTO dto) {
        product.activate();
        extractInputProductList(product, dto);
        save(product);
    }

    public Optional<Product> findById(UUID id) {
        return repository.findByIdAndActiveIsTrue(id);
    }

    public PageImpl<ProductResponseDTO> search(String name, Integer page, Integer pageSize) {
        Specification<Product> specs = Stream.of(
                optSpec(name, ProductSpecs::nameLike)
        ).reduce(Specification.allOf((root, query, cb) -> cb.isTrue(root.get("active"))), Specification::and);

        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Product> products = repository.findAll(specs, pageable);
        List<ProductResponseDTO> sortedContent = products.getContent()
                .stream().map(product ->
                        mapper.entityToResponse(product, calculateMaxProduction(product), calculateTotalValue(product)))
                .sorted(Comparator.comparing(ProductResponseDTO::totalValue).reversed())
                .toList();

        return new PageImpl<>(sortedContent, products.getPageable(), products.getTotalElements());
    }

    public List<Product> searchProductsById(UUID id) {
        return repository.findProductsByInputId(id);
    }

    @Transactional
    public void update(Product product, ProductRequestDTO dto) {
        if (product.getId() == null)
            throw new IllegalArgumentException("Is necessary to have a saved product in db to update it");

        product.setName(dto.name());
        product.setValue(BigDecimal.valueOf(dto.value()));
        extractInputProductList(product, dto);

        save(product);
    }

    public int calculateMaxProduction(Product product) {
        return product.getInputs().stream()
                .map(inputs -> {
                    BigDecimal required = inputs.getAmount();
                    BigDecimal total = BigDecimal.valueOf(inputs.getInput().getAmount());

                    if (total.equals(BigDecimal.ZERO))
                        return 0;
                    return total.divide(required, RoundingMode.DOWN);
                })
                .mapToInt(Number::intValue)
                .min()
                .orElse(0);
    }

    public BigDecimal calculateTotalValue(Product product) {
        return product.getValue().multiply(BigDecimal.valueOf(calculateMaxProduction(product)));
    }

    public void extractInputProductList(Product product, ProductRequestDTO dto) {
        Map<InputProductId, InputProduct> existing = product.getInputs().stream()
                .collect(Collectors.toMap(InputProduct::getId, Function.identity()));

        Set<InputProduct> updated = new HashSet<>();
        for (RequiredInputRequestDTO required : dto.inputs()) {
            Input input = inputRepository
                    .findById(UUID.fromString(required.id()))
                    .orElseThrow(() -> new EntityNotFoundException("Input not found with id:"  + required.id()));

            InputProductId id = new InputProductId(product.getId(), input.getId());
            InputProduct association = existing.get(id);

            if (association == null) {
                association = new InputProduct();
                association.setId(id);
                association.setProduct(product);
                association.setInput(input);
            }

            association.setAmount(BigDecimal.valueOf(required.amount()));
            updated.add(association);
        }

        product.getInputs().clear();
        product.getInputs().addAll(updated);
    }

    @Transactional
    public void delete(Product product) {
        repository.delete(product);
    }
}
