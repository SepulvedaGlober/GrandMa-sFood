package project.grandmasfood.infrastructure.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.grandmasfood.application.dto.client.ClientRequestDto;
import project.grandmasfood.application.dto.client.ClientResponseDto;
import project.grandmasfood.application.dto.client.ClientUpdateRequestDto;
import project.grandmasfood.application.handler.interfaces.IClientHandler;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientRestController {

    private final IClientHandler clientHandler;

    @Operation(summary = "Create a new client", description = "Endpoint for creating a new client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Client created", content = @Content(schema = @Schema(implementation = ClientResponseDto.class))),
            @ApiResponse(responseCode = "409", description = "Conflict: A client with the same name already exists"),
            @ApiResponse(responseCode = "400", description = "Invalid client data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<ClientResponseDto> createClient(@RequestBody ClientRequestDto clientRequestDto) {
        try{
            ClientResponseDto clientResponseDto = clientHandler.createClient(clientRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(clientResponseDto);

        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    //SEARCH CLIENT BY DOCUMENT
    @Operation(summary = "Search client", description = "Search for a client by document")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client found", content = @Content(schema = @Schema(implementation = ClientResponseDto.class))),
            @ApiResponse(responseCode = "409", description = "Conflict: A client with the same document already exists"),
            @ApiResponse(responseCode = "404", description = "Client not found"),
            @ApiResponse(responseCode = "400", description = "Invalid document format"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{document}")
    public ResponseEntity<ClientResponseDto> getClientByDocument(@PathVariable String document) {
        Optional<ClientResponseDto> clientResponseDto = clientHandler.getClientByDocument(document);
        return clientResponseDto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    //UPDATE CLIENT BY DOCUMENT
    @Operation(summary = "Update client", description = "Update a client by document")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Client updated", content = @Content(schema = @Schema(implementation = ClientResponseDto.class))),
            @ApiResponse(responseCode = "409", description = "Conflict: A client with the same document already exists"),
            @ApiResponse(responseCode = "404", description = "Client not found"),
            @ApiResponse(responseCode = "400", description = "Invalid document format"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{document}")
    public ResponseEntity<ClientResponseDto> updateClient(@PathVariable String document, @Validated @RequestBody ClientUpdateRequestDto clientUpdateRequestDto) {
        try{
            clientHandler.updateClient(document, clientUpdateRequestDto);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);

        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    //DELETE CLIENT BY DOCUMENT
    @Operation(summary = "Delete client", description = "Delete a client by document")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Client deleted", content = @Content(schema = @Schema(implementation = ClientResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Client not found"),
            @ApiResponse(responseCode = "400", description = "Invalid document format"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{document}")
    public ResponseEntity<Void> deleteClient(@PathVariable String document) {
        clientHandler.deleteClient(document);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    //GET ALL CLIENTS
    @Operation(summary = "Get all clients", description = "Get all clients")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clients found", content = @Content(schema = @Schema(implementation = ClientResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "No clients found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<ClientResponseDto>> getAllClients() {
        List<ClientResponseDto> clientResponseDto = clientHandler.getAllClients();
        if (clientResponseDto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(clientResponseDto);
    }
}
