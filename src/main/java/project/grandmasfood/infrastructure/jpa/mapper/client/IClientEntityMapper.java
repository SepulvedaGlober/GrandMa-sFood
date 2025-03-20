package project.grandmasfood.infrastructure.jpa.mapper.client;

import org.mapstruct.Mapper;
import project.grandmasfood.domain.models.Client;
import project.grandmasfood.infrastructure.jpa.entities.ClientEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IClientEntityMapper {

    /**
     * Transform a client to a client entity
     *
     * @param client client to transform
     * @return client entity
     */
    ClientEntity toEntity(Client client);

    /**
     * Transform a client entity to a client
     *
     * @param clientEntity client entity to transform
     * @return client
     */
    Client toClient(ClientEntity clientEntity);

    /**
     * Transform a list of client entities to a list of clients
     *
     * @param clientEntities list of client entities to transform
     * @return list of clients
     */
    List<Client> toClientList(List<ClientEntity> clientEntities);
}
