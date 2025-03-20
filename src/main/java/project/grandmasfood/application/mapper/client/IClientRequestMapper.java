package project.grandmasfood.application.mapper.client;

import org.mapstruct.Mapper;
import project.grandmasfood.application.dto.client.ClientRequestDto;
import project.grandmasfood.application.dto.client.ClientUpdateRequestDto;
import project.grandmasfood.domain.models.Client;

@Mapper(componentModel = "spring")
public interface IClientRequestMapper {

    /**
     * Transforms a ClientRequestDto into a Client
     *
     * @param clientRequestDto the client request dto
     * @return the client
     */
    Client toClient(ClientRequestDto clientRequestDto);

    /**
     * Transforms a ClientUpdateRequestDto into a Client
     *
     * @param clientUpdateRequestDto the client update request dto
     * @return the client
     */
    Client toClient(ClientUpdateRequestDto clientUpdateRequestDto);
}
