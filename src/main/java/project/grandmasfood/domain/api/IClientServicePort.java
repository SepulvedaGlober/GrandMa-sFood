package project.grandmasfood.domain.api;

import project.grandmasfood.domain.models.Client;

import java.util.List;
import java.util.Optional;

public interface IClientServicePort {
    void createClient(Client client);
    void updateClient(String document, Client client);
    void deleteClient(String document);
    List<Client> getAllClients();
    Optional<Client> getClientByDocument(String document);
}
