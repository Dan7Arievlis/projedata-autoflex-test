package io.github.dan7arievlis.autoflextest.repository;

import io.github.dan7arievlis.autoflextest.model.Input;
import io.github.dan7arievlis.autoflextest.model.InputProduct;
import io.github.dan7arievlis.autoflextest.model.embedded.InputProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface InputProductRepository extends JpaRepository<InputProduct, InputProductId> {
    boolean existsByInput(Input input);

    @Query("""
        SELECT pi
        FROM InputProduct pi
        WHERE pi.product.id = :productId
    """)
    List<InputProduct> findInputsByProductId(UUID productId);
}
