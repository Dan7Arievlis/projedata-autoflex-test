package io.github.dan7arievlis.autoflextest.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Table(name = "lgpd_audit")
@EntityListeners(AuditingEntityListener.class)
public class LgpdAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String entityName;
    private UUID entityId;
    private String operation;
    private String reason;

    private LocalDateTime processedAt;
}
