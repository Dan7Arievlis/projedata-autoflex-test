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
@ToString(exclude = "products")
@EqualsAndHashCode(callSuper = true)
@Table(name = "tb_input")
@EntityListeners(AuditingEntityListener.class)
public class Input extends Base {
    @Column(name = "name", length = 100,  nullable = false, unique = true)
    private String name;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @OneToMany(
            mappedBy = "input",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    private Set<InputProduct> products = new HashSet<>();
}
