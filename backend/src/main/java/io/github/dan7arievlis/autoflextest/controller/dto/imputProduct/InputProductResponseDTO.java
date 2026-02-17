package io.github.dan7arievlis.autoflextest.controller.dto.imputProduct;

import java.util.UUID;

public record InputProductResponseDTO(
    UUID id,
    UUID inputId,
    String inputName,
    Double amount,
    Double totalAmount
) {
}
