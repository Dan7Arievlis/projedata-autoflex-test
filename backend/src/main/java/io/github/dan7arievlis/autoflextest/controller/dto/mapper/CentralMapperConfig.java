package io.github.dan7arievlis.autoflextest.controller.dto.mapper;

import org.mapstruct.MapperConfig;

@MapperConfig(componentModel = "spring", uses = BaseMapper.class)
public interface CentralMapperConfig {}

