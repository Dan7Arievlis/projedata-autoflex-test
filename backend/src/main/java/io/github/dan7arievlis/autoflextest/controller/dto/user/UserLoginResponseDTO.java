package io.github.dan7arievlis.autoflextest.controller.dto.user;

import java.util.List;

public record UserLoginResponseDTO(
        String token,
        List<String> roles,
        String login,
        String email
) {
}
