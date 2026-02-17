package io.github.dan7arievlis.autoflextest.controller.dto.product;

import io.github.dan7arievlis.autoflextest.controller.dto.input.RequiredInputRequestDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;

@Schema(name = "InputRequest")
public record ProductRequestDTO(
        @NotBlank(message = "obligatory field.")
        @Size(min = 2, max = 100, message = "field out of bounds. ({min} - {max})")
//        @Schema(name = "name")
        String name,
        @Positive(message = "obligatory field.")
//        @Schema(name = "value")
        Double value,
        @NotEmpty(message = "obligatory field.")
//        @Schema(name = "inputs")
        List<RequiredInputRequestDTO> inputs) {
    
}
