package project.grandmasfood.domain.spi;

import project.grandmasfood.domain.models.Client;

import java.util.List;
import java.util.Optional;

public interface IClientPersistencePort {
    void createClient(Client client);
    void updateClient(Client client);
    void deleteClient(String document);
    List<Client> getAllClients();
    Optional<Client> getClientByDocument(String document);
}
