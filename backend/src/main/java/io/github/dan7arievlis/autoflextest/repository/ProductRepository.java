package io.github.dan7arievlis.autoflextest.repository;

import io.github.dan7arievlis.autoflextest.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {
    Optional<Product> findByIdAndActiveIsTrue(UUID id);

    Optional<Product> findByNameIgnoreCase(String name);

    @Query("""
        SELECT pi.product
        FROM InputProduct pi
        WHERE pi.input.id = :inputId
    """)
    List<Product> findProductsByInputId(UUID inputId);
}
