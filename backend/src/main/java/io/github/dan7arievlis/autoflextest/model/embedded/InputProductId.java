package io.github.dan7arievlis.autoflextest.model.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class InputProductId implements Serializable {

    @Column(name = "product_id")
    private UUID productId;

    @Column(name = "input_id")
    private UUID inputId;
}