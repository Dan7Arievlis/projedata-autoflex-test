package io.github.dan7arievlis.autoflextest.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.util.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "inputs")
@EqualsAndHashCode(callSuper = true)
@Table(name = "product")
@EntityListeners(AuditingEntityListener.class)
public class Product extends Base {
    @Column(name = "name", length = 100, nullable = false, unique = true)
    private String name;

    @Column(name = "value", precision = 18, scale = 2, nullable = false)
    private BigDecimal value;

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    private Set<InputProduct> inputs = new HashSet<>();
}
