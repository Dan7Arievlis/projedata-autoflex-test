package io.github.dan7arievlis.autoflextest.controller.dto.mapper;

import io.github.dan7arievlis.autoflextest.controller.dto.input.*;
import io.github.dan7arievlis.autoflextest.model.Input;
import io.github.dan7arievlis.autoflextest.model.InputProduct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        config = CentralMapperConfig.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface InputMapper {

    Input requestToEntity(InputCreateRequestDTO dto);

    Input updateToEntity(InputUpdateRequestDTO dto);

    InputResponseDTO entityToResponse(Input input);

    @Mapping(source = "input.name", target = "name")
    NestedInputResponseDTO entityToNestedResponse(InputProduct association);

    Input requiredInputRequestToEntity(RequiredInputRequestDTO dto);
}
