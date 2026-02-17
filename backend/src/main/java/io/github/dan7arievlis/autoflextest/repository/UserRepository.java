package io.github.dan7arievlis.autoflextest.repository;

import io.github.dan7arievlis.autoflextest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByLogin(String login);

    Optional<User> findByLoginIgnoreCase(String login);

    @Query("""
        SELECT u.id
        FROM User u
        WHERE u.deletedAt IS NOT NULL
          AND u.deletedAt < :limit
    """)
    List<UUID> findExpiredUserIds(@Param("limit") LocalDateTime limit);

    @Modifying
    @Query("""
        DELETE FROM User u
        WHERE u.id IN :ids
    """)
    void deleteByIds(@Param("ids") List<UUID> ids);

    Optional<User> findByEmail(String email);
}
