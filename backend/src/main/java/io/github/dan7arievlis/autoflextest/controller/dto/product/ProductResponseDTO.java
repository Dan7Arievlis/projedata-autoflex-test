package io.github.dan7arievlis.autoflextest.controller.dto.product;

import io.github.dan7arievlis.autoflextest.controller.dto.base.BaseResponseDTO;
import io.github.dan7arievlis.autoflextest.controller.dto.input.NestedInputResponseDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ProductResponseDTO(
    UUID id,
    String name,
    Double value,
    Integer maxProduction,
    BigDecimal totalValue,
    List<NestedInputResponseDTO> inputs
) {
}
