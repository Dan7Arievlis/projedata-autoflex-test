package io.github.dan7arievlis.autoflextest.repository;

import io.github.dan7arievlis.autoflextest.model.LgpdAudit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuditRepository extends JpaRepository<LgpdAudit, UUID> {
}
