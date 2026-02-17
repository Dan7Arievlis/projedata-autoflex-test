package io.github.dan7arievlis.autoflextest.service;

import io.github.dan7arievlis.autoflextest.model.LgpdAudit;
import io.github.dan7arievlis.autoflextest.model.User;
import io.github.dan7arievlis.autoflextest.repository.AuditRepository;
import io.github.dan7arievlis.autoflextest.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class LgpdService {
    private final AuditRepository auditRepository;
    private final UserRepository userRepository;

    @Transactional
    public void processDeletion(List<UUID> userIds) {
        if (userIds.isEmpty()) return;

        LocalDateTime now = LocalDateTime.now();
        List<LgpdAudit> audits = userIds.stream()
                .map(id -> {
                    LgpdAudit audit = new LgpdAudit();
                    audit.setEntityName("User");
                    audit.setEntityId(id);
                    audit.setOperation("DELETE");
                    audit.setReason("RETENTION_EXPIRED");
                    audit.setProcessedAt(now);
                    return audit;
                })
                .toList();

        auditRepository.saveAll(audits);
        userRepository.deleteByIds(userIds);
    }
}
