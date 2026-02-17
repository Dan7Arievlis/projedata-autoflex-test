package io.github.dan7arievlis.autoflextest.controller.dto.base;

import java.util.UUID;

public record BaseResponseDTO(
        UUID id,
//        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
//        LocalDateTime createdAt,
//        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
//        LocalDateTime updatedAt,
//        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
//        LocalDateTime deletedAt,
        Boolean active
//        UUID lastUpdatedByUser
) {
}

