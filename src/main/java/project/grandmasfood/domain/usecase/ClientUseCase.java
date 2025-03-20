package project.grandmasfood.domain.usecase;

import lombok.RequiredArgsConstructor;
import project.grandmasfood.domain.api.IClientServicePort;
import project.grandmasfood.domain.exceptions.*;
import project.grandmasfood.domain.models.Client;
import project.grandmasfood.domain.spi.IClientPersistencePort;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static project.grandmasfood.utils.Constants.*;
import static project.grandmasfood.utils.ErrorMessages.NO_CLIENT_FOUND_EXCEPTION;


@RequiredArgsConstructor
public class ClientUseCase implements IClientServicePort {

    private final IClientPersistencePort clientPersistencePort;

    @Override
    public void createClient(Client client) {
        validateClient(client);
        Optional<Client> existingClient = clientPersistencePort.getClientByDocument(client.getDocument());
        if (existingClient.isPresent()) {
            throw new ClientAlreadyExistsException("Client already exists");
        }
        clientPersistencePort.createClient(client);


    }

    @Override
    public void updateClient(String document, Client client) {
        Client existingClient = clientPersistencePort.getClientByDocument(client.getDocument())
                .orElseThrow(() -> new ClientNotFoundException(NO_CLIENT_FOUND_EXCEPTION));

        validateClient(client);
        existingClient.setFullName(client.getFullName());
        existingClient.setEmail(client.getEmail());
        existingClient.setPhone(client.getPhone());
        existingClient.setDeliveryAddress(client.getDeliveryAddress());
        clientPersistencePort.updateClient(client);

    }

    @Override
    public void deleteClient(String document) {
        Client client = clientPersistencePort.getClientByDocument(document)
                .orElseThrow(() -> new ClientNotFoundException(NO_CLIENT_FOUND_EXCEPTION));
        clientPersistencePort.deleteClient(client.getDocument());
    }

    @Override
    public List<Client> getAllClients() {
        List<Client> clients = clientPersistencePort.getAllClients();
        if (clients.isEmpty()){
            throw new ClientNotFoundException(NO_CLIENT_FOUND_EXCEPTION);
        }
        return clients;
    }

    @Override
    public Optional<Client> getClientByDocument(String document) {
        return Optional.ofNullable(clientPersistencePort.getClientByDocument(document)
                .orElseThrow(() -> new ClientNotFoundException(NO_CLIENT_FOUND_EXCEPTION)));
    }

    private boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_VALIDATION_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    private boolean validatePhone(String phone) {
        Pattern pattern = Pattern.compile(PHONE_VALIDATION_REGEX);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches() && phone.length() <= MAX_PHONE_LENGTH;
    }
    private boolean validateDocument(String document) {
        Pattern pattern = Pattern.compile(DOCUMENT_VALIDATION_REGEX);
        Matcher matcher = pattern.matcher(document);
        return matcher.matches() && document.length() <= MAX_DOCUMENT_LENGTH;
    }
    private void validateClient(Client client) {
        if (!validateEmail(client.getEmail()) || client.getEmail().length() > MAX_EMAIL_LENGTH) {
            throw new InvalidEmailException("Invalid email");
        }
        if (!validatePhone(client.getPhone())) {
            throw new InvalidPhoneException("Invalid phone");
        }
        if (!validateDocument(client.getDocument())) {
            throw new InvalidDocumentException("Invalid document");
        }
        if (client.getFullName().length() > MAX_FULL_NAME_LENGTH) {
            throw new InvalidNameException("Invalid full name");
        }
        if (client.getDeliveryAddress().length() > MAX_ADDRESS_LENGTH) {
            throw new InvalidAddressException("Invalid deliveryAddress");
        }
    }
}
