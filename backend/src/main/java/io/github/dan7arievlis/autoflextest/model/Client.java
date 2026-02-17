package io.github.dan7arievlis.autoflextest.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

/**
 * Entidade técnica de OAuth.
 * Não representa pessoa natural.
 */
@Entity
@Data
@Table(name = "client")
public class Client extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "client_id", length = 150, nullable = false, unique = true)
    private String clientId;

    @Column(name = "client_secret", length = 400, nullable = false)
    private String clientSecret;

    @Column(name = "redirect_uri", length = 200, nullable = false)
    private String redirectURI;

    @Column(name = "scope", length = 50)
    private String Scope;
}
