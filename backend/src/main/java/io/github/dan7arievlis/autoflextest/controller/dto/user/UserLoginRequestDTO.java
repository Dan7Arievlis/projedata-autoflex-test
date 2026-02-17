package io.github.dan7arievlis.autoflextest.controller.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UserLoginRequestDTO(
        @NotBlank(message = "obligatory field.")
        @Size(min = 3, max = 30, message = "field must have at least {min} characters")
        String login,
        @NotBlank(message = "obligatory field.")
        @Size(min = 8, message = "field must have at least {min} characters")
        String password
) {
}
