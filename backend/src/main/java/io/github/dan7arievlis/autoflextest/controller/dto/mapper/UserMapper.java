package io.github.dan7arievlis.autoflextest.controller.dto.mapper;

import io.github.dan7arievlis.autoflextest.controller.dto.user.*;
import io.github.dan7arievlis.autoflextest.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        config = CentralMapperConfig.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {

    User RequestToEntity(UserCreateRequestDTO dto);

    User loginRequestToEntity(UserLoginRequestDTO dto);

    UserLoginResponseDTO EntityToResponse(User user);

    @Mapping(source = "user", target = "metadata")
    UserResponseDTO entityToResponse(User user);
}
