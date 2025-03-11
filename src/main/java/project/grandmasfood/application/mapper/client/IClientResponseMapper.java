package project.grandmasfood.application.mapper.client;

import org.mapstruct.Mapper;
import project.grandmasfood.application.dto.client.ClientResponseDto;
import project.grandmasfood.domain.models.Client;

@Mapper(componentModel = "spring")
public interface IClientResponseMapper {

    ClientResponseDto toResponseDto(Client client);
}
