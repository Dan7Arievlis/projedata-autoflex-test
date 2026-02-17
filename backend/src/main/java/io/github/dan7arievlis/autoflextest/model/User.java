package io.github.dan7arievlis.autoflextest.model;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "tb_user")
@EntityListeners(AuditingEntityListener.class)
public class User extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "login", length = 20, nullable = false, unique = true)
    private String login;

    @Column(name = "password", length = 300, nullable = false)
    private String password;

    @Column(name = "email", length = 150, nullable = false)
    private String email;

    @Type(ListArrayType.class)
    @Column(name = "roles", columnDefinition = "varchar[]")
    private List<String> roles = new ArrayList<>();

    @CreatedDate
    @Column(name = "registration_date")
    private LocalDateTime registrationDate;
}
