package io.github.dan7arievlis.autoflextest.service;

import io.github.dan7arievlis.autoflextest.model.Client;
import io.github.dan7arievlis.autoflextest.repository.ClientRepository;
import io.github.dan7arievlis.autoflextest.validator.ClientValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository repository;
    private final ClientValidator validator;
    private final PasswordEncoder encoder;

    public Client create(Client client) {
        client.setClientSecret(encoder.encode(client.getClientSecret()));
        validator.validate(client);
        return repository.save(client);
    }

    public Client findByClientId(String clientId) {
        return repository.findByClientId(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + clientId));
    }

    public void delete(Client client) {
        client.setClientSecret(null);
        client.setRedirectURI(null);
        client.setScope(null);
        client.delete();
        repository.save(client);
    }
}
