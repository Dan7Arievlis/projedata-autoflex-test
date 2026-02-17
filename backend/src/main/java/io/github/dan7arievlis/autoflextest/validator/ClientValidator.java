package io.github.dan7arievlis.autoflextest.validator;

import io.github.dan7arievlis.autoflextest.exceptions.DuplicatedRegisterException;
import io.github.dan7arievlis.autoflextest.model.Client;
import io.github.dan7arievlis.autoflextest.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClientValidator {
    private final ClientRepository repository;

    public void validate(Client client) {
        if (existsRegisteredClient(client)) {
            throw new DuplicatedRegisterException("Client already exists");
        }
    }

    private boolean existsRegisteredClient(Client client) {
        Optional<Client> foundClient = repository.findByClientId(client.getClientId());

        if(client.getId() == null)
            return foundClient.isPresent();

        return foundClient.isPresent() && !client.getId().equals(foundClient.get().getId());
    }
}
