package io.github.dan7arievlis.autoflextest.repository.specs;

import io.github.dan7arievlis.autoflextest.model.Input;
import io.github.dan7arievlis.autoflextest.model.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecs {
    public static Specification<Product> nameLike(String name) {
        return (root, query, cb) -> cb.like(cb.upper(root.get("name")), "%" + name.toUpperCase() + "%");
    }
}
