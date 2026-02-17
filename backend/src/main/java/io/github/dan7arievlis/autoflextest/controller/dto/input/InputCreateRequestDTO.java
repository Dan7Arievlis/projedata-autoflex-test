package io.github.dan7arievlis.autoflextest.controller.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(name = "InputRequest")
public record InputCreateRequestDTO(
        @NotBlank(message = "obligatory field.")
        @Size(min = 2, max = 100, message = "field out of bounds. ({min} - {max})")
//        @Schema(name = "name")
        String name,
        @NotNull(message = "obligatory field.")
        @PositiveOrZero(message = "field must be not negative.")
//        @Schema(name = "amount")
        Double amount) {
    
}
