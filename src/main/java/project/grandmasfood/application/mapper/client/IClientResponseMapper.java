package project.grandmasfood.application.mapper.client;

import org.mapstruct.Mapper;
import project.grandmasfood.application.dto.client.ClientResponseDto;
import project.grandmasfood.domain.models.Client;

@Mapper(componentModel = "spring")
public interface IClientResponseMapper {

    /**
     * Transforms a Client into a ClientResponseDto
     *
     * @param client the client to transform
     * @return the transformed client
     */
    ClientResponseDto toResponseDto(Client client);
}
