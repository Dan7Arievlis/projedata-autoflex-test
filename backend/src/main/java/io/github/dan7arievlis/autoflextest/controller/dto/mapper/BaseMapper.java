package io.github.dan7arievlis.autoflextest.controller.dto.mapper;

import io.github.dan7arievlis.autoflextest.controller.dto.base.BaseResponseDTO;
import io.github.dan7arievlis.autoflextest.model.Base;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BaseMapper {

    BaseResponseDTO entityToResponse(Base base);
}
