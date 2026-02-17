package io.github.dan7arievlis.autoflextest.controller.dto.input;

import java.util.UUID;

public record InputResponseDTO(
    UUID id,
    String name,
    Double amount
) {
}
