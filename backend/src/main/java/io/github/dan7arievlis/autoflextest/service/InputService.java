package io.github.dan7arievlis.autoflextest.service;

import io.github.dan7arievlis.autoflextest.controller.dto.input.InputUpdateRequestDTO;
import io.github.dan7arievlis.autoflextest.exceptions.OperationNotAllowedException;
import io.github.dan7arievlis.autoflextest.model.Input;
import io.github.dan7arievlis.autoflextest.model.InputProduct;
import io.github.dan7arievlis.autoflextest.model.Product;
import io.github.dan7arievlis.autoflextest.repository.InputProductRepository;
import io.github.dan7arievlis.autoflextest.repository.InputRepository;
import io.github.dan7arievlis.autoflextest.repository.specs.InputSpecs;
import io.github.dan7arievlis.autoflextest.security.SecurityService;
import io.github.dan7arievlis.autoflextest.service.component.OptionalSpecificationSearch;
import io.github.dan7arievlis.autoflextest.validator.InputValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class InputService implements OptionalSpecificationSearch {
    private final InputRepository repository;
    private final InputProductRepository productRepository;
    private final InputValidator validator;
    private final SecurityService securityService;
    private final InputProductRepository inputProductRepository;

    @Transactional
    public void save(Input input) {
        validator.validate(input);
        input.setLastModifiedBy(securityService.getLoggedUser());
        repository.save(input);
    }

    @Transactional
    public void create(Input input) {
        input.activate();
        save(input);
    }

    public Optional<Input> findById(UUID id) {
        return repository.findByIdAndActiveIsTrue(id);
    }

    public Page<Input> search(String name, Integer page, Integer pageSize) {
        Specification<Input> specs = Stream.of(
                optSpec(name, InputSpecs::nameLike)
        ).reduce(Specification.allOf((root, query, cb) -> cb.isTrue(root.get("active"))), Specification::and);

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.ASC, "name"));
        return repository.findAll(specs, pageable);
    }

    public List<InputProduct> searchInputsById(UUID id) {
        return productRepository.findInputsByProductId(id);
    }

    @Transactional
    public void update(Input input, InputUpdateRequestDTO dto) {
        if (input.getId() == null)
            throw new IllegalArgumentException("Is necessary to have a saved input in db to update it.");

        input.setName(dto.name());
        input.setAmount(dto.amount());

        save(input);
    }

    @Transactional
    public void delete(Input input) {
        if (inputProductRepository.existsByInput(input))
            throw new OperationNotAllowedException("Can't delete input that is used by products.");
        repository.delete(input);
    }
}
