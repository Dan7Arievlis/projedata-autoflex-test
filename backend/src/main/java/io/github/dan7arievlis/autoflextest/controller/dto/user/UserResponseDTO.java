package io.github.dan7arievlis.autoflextest.controller.dto.user;

import io.github.dan7arievlis.autoflextest.controller.dto.base.BaseResponseDTO;

public record UserResponseDTO(
        String login,
        String email,
        BaseResponseDTO metadata
) {
}

