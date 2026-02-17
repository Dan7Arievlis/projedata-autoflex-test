package io.github.dan7arievlis.autoflextest.controller.dto.mapper;

import io.github.dan7arievlis.autoflextest.controller.dto.imputProduct.InputProductResponseDTO;
import io.github.dan7arievlis.autoflextest.model.InputProduct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        config = CentralMapperConfig.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface InputProductMapper {
    @Mapping(source = "product.id", target = "id")
    @Mapping(source = "input.id", target = "inputId")
    @Mapping(source = "input.name", target = "inputName")
    @Mapping(source = "input.amount", target = "totalAmount")
    InputProductResponseDTO entityToResponse(InputProduct inputProduct);
}
