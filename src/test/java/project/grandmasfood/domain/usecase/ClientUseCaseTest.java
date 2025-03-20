package project.grandmasfood.domain.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.grandmasfood.domain.exceptions.*;
import project.grandmasfood.domain.models.Client;
import project.grandmasfood.domain.spi.IClientPersistencePort;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientUseCaseTest {

    @Mock
    private IClientPersistencePort clientPersistencePort;

    @InjectMocks
    private ClientUseCase clientUseCase;

    private Client validClient;

    @BeforeEach
    void setUp() {
        validClient = new Client();
        validClient.setDocument("CC-1234567");
        validClient.setFullName("Sebas Patino");
        validClient.setEmail("sebas@example.com");
        validClient.setPhone("3001234567");
        validClient.setDeliveryAddress("Carrera 90 #40-35");
    }


    @Test
    void createClient_Success() {
        when(clientPersistencePort.getClientByDocument(validClient.getDocument()))
                .thenReturn(Optional.empty());

        assertDoesNotThrow(() -> clientUseCase.createClient(validClient));
        verify(clientPersistencePort).createClient(validClient);
    }

    @Test
    void createClient_ThrowsException_WhenClientExists() {
        when(clientPersistencePort.getClientByDocument(validClient.getDocument()))
                .thenReturn(Optional.of(validClient));

        assertThrows(ClientAlreadyExistsException.class,
                () -> clientUseCase.createClient(validClient));
        verify(clientPersistencePort, never()).createClient(any());
    }

    @Test
    void createClient_ThrowsException_WhenInvalidEmail() {
        validClient.setEmail("invalid-email");

        assertThrows(InvalidEmailException.class,
                () -> clientUseCase.createClient(validClient));
        verify(clientPersistencePort, never()).createClient(any());
    }

    @Test
    void createClient_ThrowsException_WhenInvalidPhone() {
        validClient.setPhone("123"); // Invalid phone

        assertThrows(InvalidPhoneException.class,
                () -> clientUseCase.createClient(validClient));
        verify(clientPersistencePort, never()).createClient(any());
    }

    @Test
    void createClient_ThrowsException_WhenInvalidDocument() {
        validClient.setDocument("abc"); // Invalid document

        assertThrows(InvalidDocumentException.class,
                () -> clientUseCase.createClient(validClient));
        verify(clientPersistencePort, never()).createClient(any());
    }

    @Test
    void createClient_ThrowsException_WhenNameTooLong() {
        validClient.setFullName("a".repeat(256)); // Exceeds max length

        assertThrows(InvalidNameException.class,
                () -> clientUseCase.createClient(validClient));
        verify(clientPersistencePort, never()).createClient(any());
    }

    @Test
    void createClient_ThrowsException_WhenAddressTooLong() {
        validClient.setDeliveryAddress("a".repeat(501)); // Exceeds max length

        assertThrows(InvalidAddressException.class,
                () -> clientUseCase.createClient(validClient));
        verify(clientPersistencePort, never()).createClient(any());
    }

    @Test
    void updateClient_Success() {
        when(clientPersistencePort.getClientByDocument(validClient.getDocument()))
                .thenReturn(Optional.of(validClient));

        assertDoesNotThrow(() -> clientUseCase.updateClient(
                validClient.getDocument(), validClient));
        verify(clientPersistencePort).updateClient(validClient);
    }

    @Test
    void updateClient_ThrowsException_WhenClientNotFound() {
        when(clientPersistencePort.getClientByDocument(validClient.getDocument()))
                .thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class,
                () -> clientUseCase.updateClient(validClient.getDocument(), validClient));
        verify(clientPersistencePort, never()).updateClient(any());
    }

    @Test
    void deleteClient_Success() {
        when(clientPersistencePort.getClientByDocument(validClient.getDocument()))
                .thenReturn(Optional.of(validClient));

        assertDoesNotThrow(() ->
                clientUseCase.deleteClient(validClient.getDocument()));
        verify(clientPersistencePort).deleteClient(validClient.getDocument());
    }

    @Test
    void deleteClient_ThrowsException_WhenClientNotFound() {
        when(clientPersistencePort.getClientByDocument(validClient.getDocument()))
                .thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class,
                () -> clientUseCase.deleteClient(validClient.getDocument()));
        verify(clientPersistencePort, never()).deleteClient(any());
    }

    @Test
    void getAllClients_Success() {
        List<Client> clients = Arrays.asList(validClient, validClient);
        when(clientPersistencePort.getAllClients()).thenReturn(clients);

        List<Client> result = clientUseCase.getAllClients();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(clientPersistencePort).getAllClients();
    }

    @Test
    void getAllClients_ThrowsException_WhenNoClients() {
        when(clientPersistencePort.getAllClients())
                .thenReturn(Collections.emptyList());

        assertThrows(ClientNotFoundException.class,
                () -> clientUseCase.getAllClients());
        verify(clientPersistencePort).getAllClients();
    }

    @Test
    void getClientByDocument_Success() {
        when(clientPersistencePort.getClientByDocument(validClient.getDocument()))
                .thenReturn(Optional.of(validClient));

        Optional<Client> result =
                clientUseCase.getClientByDocument(validClient.getDocument());

        assertTrue(result.isPresent());
        assertEquals(validClient, result.get());
        verify(clientPersistencePort).getClientByDocument(validClient.getDocument());
    }

    @Test
    void getClientByDocument_ThrowsException_WhenClientNotFound() {
        when(clientPersistencePort.getClientByDocument(validClient.getDocument()))
                .thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> clientUseCase.getClientByDocument(validClient.getDocument()));
        verify(clientPersistencePort).getClientByDocument(validClient.getDocument());
    }
}