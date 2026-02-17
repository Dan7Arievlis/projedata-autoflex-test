package io.github.dan7arievlis.autoflextest.repository.specs;

import io.github.dan7arievlis.autoflextest.model.Input;
import org.springframework.data.jpa.domain.Specification;

public class InputSpecs {
    public static Specification<Input> nameLike(String name) {
        return (root, query, cb) -> cb.like(cb.upper(root.get("name")), "%" + name.toUpperCase() + "%");
    }
}
