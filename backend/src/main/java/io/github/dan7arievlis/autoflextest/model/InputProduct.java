package io.github.dan7arievlis.autoflextest.model;

import io.github.dan7arievlis.autoflextest.model.embedded.InputProductId;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@Table(name = "input_product")
@EntityListeners(AuditingEntityListener.class)
public class InputProduct {
    @EmbeddedId
    private InputProductId id = new InputProductId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @MapsId("inputId")
    @JoinColumn(name = "input_id")
    private Input input;

    @Column(name = "amount", precision = 15, scale = 3, nullable = false)
    private BigDecimal amount;

    /*
        InputProduct ip = new InputProduct();
        ip.setProduct(product);
        ip.setInput(input);
        ip.setAmount(new BigDecimal("2.5"));

        product.getInputs().add(ip);
        input.getProducts().add(ip);
     */
}
