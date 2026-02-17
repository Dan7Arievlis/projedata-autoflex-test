package io.github.dan7arievlis.autoflextest.controller.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record InputUpdateRequestDTO(
        @NotBlank(message = "obligatory field.")
        @Size(min = 2, max = 100, message = "field out of bounds. ({min} - {max})")
//        @Schema(name = "name")
        String name,
        @NotNull(message = "obligatory field.")
        @PositiveOrZero(message = "field must be not negative.")
//        @Schema(name = "amount")
        Double amount
) {
}
