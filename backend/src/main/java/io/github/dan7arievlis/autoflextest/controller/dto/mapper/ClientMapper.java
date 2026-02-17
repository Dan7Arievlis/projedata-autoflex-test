package io.github.dan7arievlis.autoflextest.controller.dto.mapper;

import io.github.dan7arievlis.autoflextest.controller.dto.client.ClientRequestDTO;
import io.github.dan7arievlis.autoflextest.model.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    Client requestToEntity(ClientRequestDTO dto);

//    ClientRequestDTO toRequest(Client client);
}
