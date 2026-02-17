package io.github.dan7arievlis.autoflextest.validator;

import io.github.dan7arievlis.autoflextest.exceptions.DuplicatedRegisterException;
import io.github.dan7arievlis.autoflextest.model.Input;
import io.github.dan7arievlis.autoflextest.repository.InputRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class InputValidator {
    private final InputRepository repository;

    public void validate(Input input) {
        if(existsRegistered(input))
            throw new DuplicatedRegisterException("Input already exists.");
    }

    private boolean existsRegistered(Input input) {
        Optional<Input> foundEntity = repository.findByNameIgnoreCase(input.getName());
        if (input.getId() == null)
            return foundEntity.isPresent();

        return foundEntity.isPresent() && !input.getId().equals(foundEntity.get().getId());
    }
}