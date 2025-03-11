package project.grandmasfood.infrastructure.jpa.mapper.client;

import org.mapstruct.Mapper;
import project.grandmasfood.domain.models.Client;
import project.grandmasfood.infrastructure.jpa.entities.ClientEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IClientEntityMapper {

    ClientEntity toEntity(Client client);

    Client toClient(ClientEntity clientEntity);

    List<Client> toClientList(List<ClientEntity> clientEntities);
}
