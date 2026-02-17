package io.github.dan7arievlis.autoflextest.service;

import io.github.dan7arievlis.autoflextest.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CleanupService {
    private final UserRepository userRepository;
    private final LgpdService lgpdService;

    @Value("${lgpd.retention-days}")
    private long retentionDays;

    @Scheduled(cron = "0 0 2 * * ?")
    public void deleteExpiredUsers() {
        LocalDateTime limit = LocalDateTime.now().minusDays(retentionDays);
        lgpdService.processDeletion(userRepository.findExpiredUserIds(limit));
    }
}
