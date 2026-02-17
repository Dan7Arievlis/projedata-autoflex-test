package io.github.dan7arievlis.autoflextest.controller.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.UUID;

public record RequiredInputRequestDTO(
        @UUID
        @NotBlank(message = "obligatory field.")
        String id,
        @NotNull(message = "obligatory field.")
        @Positive(message = "field must be greater than zero.")
        Double amount
) {
}
