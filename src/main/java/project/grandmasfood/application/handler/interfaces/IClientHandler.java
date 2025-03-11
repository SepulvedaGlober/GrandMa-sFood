package project.grandmasfood.application.handler.interfaces;

import project.grandmasfood.application.dto.client.ClientRequestDto;
import project.grandmasfood.application.dto.client.ClientResponseDto;
import project.grandmasfood.application.dto.client.ClientUpdateRequestDto;

import java.util.List;
import java.util.Optional;

public interface IClientHandler {
    ClientResponseDto createClient(ClientRequestDto clientRequestDto);
    void updateClient(String document, ClientUpdateRequestDto clientUpdateRequestDto);
    void deleteClient(String document);
    Optional<ClientResponseDto> getClientByDocument(String document);
    List<ClientResponseDto> getAllClients();
}
