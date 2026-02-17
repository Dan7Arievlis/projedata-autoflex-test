package io.github.dan7arievlis.autoflextest.controller.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(name = "UserUpdateRequest")
public record UserUpdateRequestDTO(
        @NotBlank(message = "obligatory field.")
        @Size(min = 2, max = 30, message = "field out of bounds. ({min} - {max})")
        String login,
        @NotBlank(message = "obligatory field.")
        @Email(message = "invalid email.")
        @Size(min = 5, max = 80, message = "field out of bounds. ({min} - {max})")
        String email,
        @NotNull(message = "obligatory field.")
        String role
){}