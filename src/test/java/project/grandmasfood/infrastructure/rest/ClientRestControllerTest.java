package project.grandmasfood.infrastructure.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import project.grandmasfood.application.dto.client.ClientRequestDto;
import project.grandmasfood.application.dto.client.ClientResponseDto;
import project.grandmasfood.application.dto.client.ClientUpdateRequestDto;
import project.grandmasfood.application.handler.interfaces.IClientHandler;
import project.grandmasfood.domain.exceptions.ClientNotFoundException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ClientRestControllerTest {


    @Mock
    private IClientHandler clientHandler;

    @InjectMocks
    private ClientRestController clientRestController;

    private ClientRequestDto clientRequestDto;
    private ClientUpdateRequestDto clientUpdateRequestDto;
    private ClientResponseDto clientResponseDto;
    private String document;


    @BeforeEach
    void setUp() {
        document = "CC-123456";

        clientRequestDto = new ClientRequestDto();
        clientRequestDto.setDocument(document);
        clientRequestDto.setFullName("Julian Patino");
        clientRequestDto.setEmail("julian@example.com");
        clientRequestDto.setPhone("3001234567");
        clientRequestDto.setDeliveryAddress("Cicular 4 # 12-34");

        clientUpdateRequestDto = new ClientUpdateRequestDto();
        clientUpdateRequestDto.setFullName("Julian Updated");
        clientUpdateRequestDto.setEmail("julian.updated@example.com");
        clientUpdateRequestDto.setPhone("3009876543");
        clientUpdateRequestDto.setDeliveryAddress("456 New St");

        clientResponseDto = new ClientResponseDto();
        clientResponseDto.setDocument(document);
        clientResponseDto.setFullName("Julian Patino");
        clientResponseDto.setEmail("julian@example.com");
        clientResponseDto.setPhone("3001234567");
        clientResponseDto.setDeliveryAddress("Cicular 4 # 12-34");
    }


    @Test
    void createClient_Success() {
        when(clientHandler.createClient(any(ClientRequestDto.class)))
                .thenReturn(clientResponseDto);

        ResponseEntity<ClientResponseDto> response =
                clientRestController.createClient(clientRequestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(clientResponseDto, response.getBody());
        verify(clientHandler).createClient(clientRequestDto);
    }

    @Test
    void createClient_BadRequest() {
        when(clientHandler.createClient(any(ClientRequestDto.class)))
                .thenThrow(IllegalArgumentException.class);

        ResponseEntity<ClientResponseDto> response =
                clientRestController.createClient(clientRequestDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(clientHandler).createClient(clientRequestDto);
    }

    @Test
    void getClientByDocument_Success() {
        when(clientHandler.getClientByDocument(document))
                .thenReturn(Optional.of(clientResponseDto));

        ResponseEntity<ClientResponseDto> response =
                clientRestController.getClientByDocument(document);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(clientResponseDto, response.getBody());
        verify(clientHandler).getClientByDocument(document);
    }

    @Test
    void getClientByDocument_NotFound() {
        when(clientHandler.getClientByDocument(document))
                .thenReturn(Optional.empty());

        ResponseEntity<ClientResponseDto> response =
                clientRestController.getClientByDocument(document);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(clientHandler).getClientByDocument(document);
    }

    @Test
    void updateClient_Success() {
        doNothing().when(clientHandler)
                .updateClient(eq(document), any(ClientUpdateRequestDto.class));

        ResponseEntity<ClientResponseDto> response =
                clientRestController.updateClient(document, clientUpdateRequestDto);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(clientHandler).updateClient(document, clientUpdateRequestDto);
    }

    @Test
    void updateClient_BadRequest() {
        doThrow(IllegalArgumentException.class).when(clientHandler)
                .updateClient(eq(document), any(ClientUpdateRequestDto.class));

        ResponseEntity<ClientResponseDto> response =
                clientRestController.updateClient(document, clientUpdateRequestDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(clientHandler).updateClient(document, clientUpdateRequestDto);
    }

    @Test
    void deleteClient_Success() {
        doNothing().when(clientHandler).deleteClient(document);

        ResponseEntity<Void> response =
                clientRestController.deleteClient(document);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(clientHandler).deleteClient(document);
    }

    @Test
    void deleteClient_ThrowsException() {
        doThrow(ClientNotFoundException.class)
                .when(clientHandler).deleteClient(document);

        assertThrows(ClientNotFoundException.class, () ->
                clientRestController.deleteClient(document));
        verify(clientHandler).deleteClient(document);
    }

    @Test
    void getAllClients_Success() {
        List<ClientResponseDto> clients =
                Arrays.asList(clientResponseDto, clientResponseDto);
        when(clientHandler.getAllClients()).thenReturn(clients);

        ResponseEntity<List<ClientResponseDto>> response =
                clientRestController.getAllClients();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(clientHandler).getAllClients();
    }

    @Test
    void getAllClients_NotFound() {
        when(clientHandler.getAllClients())
                .thenReturn(Collections.emptyList());

        ResponseEntity<List<ClientResponseDto>> response =
                clientRestController.getAllClients();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(clientHandler).getAllClients();
    }
}