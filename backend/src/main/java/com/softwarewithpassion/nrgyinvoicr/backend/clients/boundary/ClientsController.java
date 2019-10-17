package com.softwarewithpassion.nrgyinvoicr.backend.clients.boundary;

import com.softwarewithpassion.nrgyinvoicr.backend.EntityNotFoundException;
import com.softwarewithpassion.nrgyinvoicr.backend.ErrorResponse;
import com.softwarewithpassion.nrgyinvoicr.backend.clients.control.ClientsService;
import com.softwarewithpassion.nrgyinvoicr.backend.clients.control.MeterAlreadyAssignedToClientException;
import com.softwarewithpassion.nrgyinvoicr.backend.clients.entity.Client;
import com.softwarewithpassion.nrgyinvoicr.backend.clients.entity.ClientsSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.URI;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping("/api/clients")
public class ClientsController {
    private final ClientsService clientsService;

    public ClientsController(ClientsService clientsService) {
        this.clientsService = clientsService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Client> createClient(@RequestBody Client client) throws MeterAlreadyAssignedToClientException {
        Client savedClient = clientsService.createClient(client);
        URI uri = MvcUriComponentsBuilder.fromController(getClass()).path("/{id}").buildAndExpand(savedClient.getId()).toUri();
        return ResponseEntity.created(uri).body(savedClient);
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<Client> getClient(@PathVariable Long id) {
        return clientsService.getClient(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntityNotFoundException(Client.class, id));
    }

    @GetMapping
    @Transactional(readOnly = true)
    public Page<Client> findClients(ClientsSearchCriteria clientsSearchCriteria) {
        return clientsService.findClients(clientsSearchCriteria);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client client) throws MeterAlreadyAssignedToClientException {
        client.setId(id);
        return clientsService.updateClient(client)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntityNotFoundException(Client.class, id));
    }

    @ExceptionHandler({MeterAlreadyAssignedToClientException.class})
    protected ResponseEntity<ErrorResponse> handleException(MeterAlreadyAssignedToClientException e) {
        return ResponseEntity.status(BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
    }
}
