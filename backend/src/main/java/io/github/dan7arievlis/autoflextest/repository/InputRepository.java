package io.github.dan7arievlis.autoflextest.repository;

import io.github.dan7arievlis.autoflextest.model.Input;
import io.github.dan7arievlis.autoflextest.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InputRepository extends JpaRepository<Input, UUID>, JpaSpecificationExecutor<Input> {
    Optional<Input> findByIdAndActiveIsTrue(UUID id);

    Optional<Input> findByNameIgnoreCase(String name);
}
