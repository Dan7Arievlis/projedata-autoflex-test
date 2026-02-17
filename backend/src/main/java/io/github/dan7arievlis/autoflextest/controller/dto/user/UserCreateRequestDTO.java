package io.github.dan7arievlis.autoflextest.controller.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UserCreateRequestDTO(
        @NotBlank(message = "obligatory field.")
        @Size(min = 3, max = 30, message = "field must have at least {min} characters")
        String login,
        @NotBlank(message = "obligatory field.")
        @Email(message = "invalid email.")
        @Size(min = 5, max = 150, message = "field out of bounds. ({min} - {max})")
        String email,
        @NotBlank(message = "obligatory field.")
        @Size(min = 8, message = "field must have at least {min} characters")
        String password,
        @NotBlank(message = "obligatory field.")
        String confirmPassword,
        @NotNull(message = "obligatory field.")
        List<String> roles
) {
}
