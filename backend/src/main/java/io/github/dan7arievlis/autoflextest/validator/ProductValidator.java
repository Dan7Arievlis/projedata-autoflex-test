package io.github.dan7arievlis.autoflextest.validator;

import io.github.dan7arievlis.autoflextest.exceptions.DuplicatedRegisterException;
import io.github.dan7arievlis.autoflextest.model.Product;
import io.github.dan7arievlis.autoflextest.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductValidator {
    private final ProductRepository repository;

    public void validate(Product product) {
        if(existsRegistered(product))
            throw new DuplicatedRegisterException("Product already exists.");
    }

    private boolean existsRegistered(Product product) {
        Optional<Product> foundEntity = repository.findByNameIgnoreCase(product.getName());
        if (product.getId() == null)
            return foundEntity.isPresent();

        return foundEntity.isPresent() && !product.getId().equals(foundEntity.get().getId());
    }
}