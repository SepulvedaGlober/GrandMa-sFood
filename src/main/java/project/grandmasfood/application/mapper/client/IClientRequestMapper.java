package project.grandmasfood.application.mapper.client;

import org.mapstruct.Mapper;
import project.grandmasfood.application.dto.client.ClientRequestDto;
import project.grandmasfood.application.dto.client.ClientUpdateRequestDto;
import project.grandmasfood.domain.models.Client;

@Mapper(componentModel = "spring")
public interface IClientRequestMapper {

    Client toClient(ClientRequestDto clientRequestDto);

    Client toClient(ClientUpdateRequestDto clientUpdateRequestDto);
}
