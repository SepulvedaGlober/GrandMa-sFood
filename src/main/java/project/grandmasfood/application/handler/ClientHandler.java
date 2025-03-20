package project.grandmasfood.application.handler;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.grandmasfood.application.dto.client.ClientRequestDto;
import project.grandmasfood.application.dto.client.ClientResponseDto;
import project.grandmasfood.application.dto.client.ClientUpdateRequestDto;
import project.grandmasfood.application.handler.interfaces.IClientHandler;
import project.grandmasfood.application.mapper.client.IClientRequestMapper;
import project.grandmasfood.application.mapper.client.IClientResponseMapper;
import project.grandmasfood.domain.api.IClientServicePort;
import project.grandmasfood.domain.models.Client;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class ClientHandler implements IClientHandler {

    private final IClientServicePort clientServicePort;
    private final IClientRequestMapper clientRequestMapper;
    private final IClientResponseMapper clientResponseMapper;


    @Override
    public ClientResponseDto createClient(ClientRequestDto clientRequestDto) {
        Client client = clientRequestMapper.toClient(clientRequestDto);
        clientServicePort.createClient(client);
        return clientResponseMapper.toResponseDto(client);
    }

    @Override
    public void updateClient(String document, ClientUpdateRequestDto clientUpdateRequestDto) {
        Client client = clientRequestMapper.toClient(clientUpdateRequestDto);
        client.setDocument(document);
        clientServicePort.updateClient(document, client);
    }

    @Override
    public void deleteClient(String document) {
        clientServicePort.deleteClient(document);
    }

    @Override
    public Optional<ClientResponseDto> getClientByDocument(String document) {
        return clientServicePort.getClientByDocument(document)
                .map(clientResponseMapper::toResponseDto);
    }

    @Override
    public List<ClientResponseDto> getAllClients() {
        return clientServicePort.getAllClients()
                .stream()
                .map(clientResponseMapper::toResponseDto)
                .toList();
    }
}
