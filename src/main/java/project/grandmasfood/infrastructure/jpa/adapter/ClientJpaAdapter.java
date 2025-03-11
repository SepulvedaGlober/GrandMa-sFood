package project.grandmasfood.infrastructure.jpa.adapter;

import lombok.RequiredArgsConstructor;
import project.grandmasfood.domain.exceptions.ClientNotFoundException;
import project.grandmasfood.domain.models.Client;
import project.grandmasfood.domain.spi.IClientPersistencePort;
import project.grandmasfood.infrastructure.exceptions.DuplicateDocumentException;
import project.grandmasfood.infrastructure.jpa.entities.ClientEntity;
import project.grandmasfood.infrastructure.jpa.mapper.client.IClientEntityMapper;
import project.grandmasfood.infrastructure.jpa.repository.IClientRepository;

import java.util.List;
import java.util.Optional;

import static project.grandmasfood.utils.ErrorMessages.CLIENT_NOT_FOUND;
import static project.grandmasfood.utils.ErrorMessages.DUPLICATE_DOCUMENT;

@RequiredArgsConstructor
public class ClientJpaAdapter implements IClientPersistencePort {

    private final IClientRepository clientRepository;
    private final IClientEntityMapper clientEntityMapper;


    @Override
    public void createClient(Client client) {
        if(clientRepository.findByDocument(client.getDocument()).isPresent()){
            throw new DuplicateDocumentException(DUPLICATE_DOCUMENT);
        }
        ClientEntity clientEntity = clientEntityMapper.toEntity(client);
        clientRepository.save(clientEntity);

    }

    @Override
    public void updateClient(Client client) {
        ClientEntity existingClient = clientRepository.findByDocument(client.getDocument())
                .orElseThrow(() -> new ClientNotFoundException(CLIENT_NOT_FOUND));

        existingClient.setFullName(client.getFullName());
        existingClient.setEmail(client.getEmail());
        existingClient.setPhone(client.getPhone());
        existingClient.setDeliveryAddress(client.getDeliveryAddress());
        clientRepository.save(existingClient);

    }

    @Override
    public void deleteClient(String document) {
        ClientEntity existingClient = clientRepository.findByDocument(document)
                .orElseThrow(() -> new ClientNotFoundException(CLIENT_NOT_FOUND));
        clientRepository.delete(existingClient);
    }

    @Override
    public List<Client> getAllClients() {
        List<ClientEntity> clientEntities = clientRepository.findAll();
        if(clientEntities.isEmpty()){
            throw new ClientNotFoundException(CLIENT_NOT_FOUND);
        }
        return clientEntityMapper.toClientList(clientEntities);
    }

    @Override
    public Optional<Client> getClientByDocument(String document) {
        return clientRepository.findByDocument(document)
                .map(clientEntityMapper::toClient);
    }
}
