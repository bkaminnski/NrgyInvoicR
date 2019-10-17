package com.softwarewithpassion.nrgyinvoicr.backend.clients.boundary;

import com.softwarewithpassion.nrgyinvoicr.backend.EntityNotFoundException;
import com.softwarewithpassion.nrgyinvoicr.backend.clients.control.ClientPlanAssignmentsService;
import com.softwarewithpassion.nrgyinvoicr.backend.clients.control.ClientsService;
import com.softwarewithpassion.nrgyinvoicr.backend.clients.entity.Client;
import com.softwarewithpassion.nrgyinvoicr.backend.clients.entity.ClientPlanAssignment;
import com.softwarewithpassion.nrgyinvoicr.backend.clients.entity.ClientPlanAssignmentsSearchCriteria;
import com.softwarewithpassion.nrgyinvoicr.backend.plans.control.InvalidExpressionException;
import com.softwarewithpassion.nrgyinvoicr.backend.plans.control.PlansService;
import com.softwarewithpassion.nrgyinvoicr.backend.plans.entity.Plan;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/api/clients")
public class ClientPlanAssignmentsController {
    private final ClientsService clientsService;
    private final PlansService plansService;
    private final ClientPlanAssignmentsService clientPlanAssignmentsService;

    public ClientPlanAssignmentsController(ClientsService clientsService, PlansService plansService, ClientPlanAssignmentsService clientPlanAssignmentsService) {
        this.clientsService = clientsService;
        this.plansService = plansService;
        this.clientPlanAssignmentsService = clientPlanAssignmentsService;
    }

    @PostMapping("/{clientId}/planAssignments")
    @Transactional
    public ResponseEntity<ClientPlanAssignment> createClientPlanAssignment(@PathVariable("clientId") long clientId, @RequestBody ClientPlanAssignment clientPlanAssignment) {
        fillInClientAndPlan(clientId, clientPlanAssignment);
        ClientPlanAssignment savedClientPlanAssignment = clientPlanAssignmentsService.createClientPlanAssignment(clientPlanAssignment);
        URI uri = MvcUriComponentsBuilder.fromController(getClass()).path("/{id}").buildAndExpand(savedClientPlanAssignment.getId()).toUri();
        return ResponseEntity.created(uri).body(savedClientPlanAssignment);
    }

    @GetMapping("/{clientId}/planAssignments")
    @Transactional(readOnly = true)
    public Page<ClientPlanAssignment> findClientPlanAssignments(@PathVariable("clientId") long clientId, ClientPlanAssignmentsSearchCriteria clientPlanAssignmentsSearchCriteria) {
        return clientPlanAssignmentsService.findClientPlanAssignments(clientId, clientPlanAssignmentsSearchCriteria);
    }

    @PutMapping("/{clientId}/planAssignments/{id}")
    @Transactional
    public ResponseEntity<ClientPlanAssignment> updatePlanVersion(@PathVariable("clientId") long clientId, @PathVariable Long id, @RequestBody ClientPlanAssignment clientPlanAssignment) throws InvalidExpressionException, IOException {
        fillInClientAndPlan(clientId, clientPlanAssignment);
        return clientPlanAssignmentsService.updateClientPlanAssignment(clientPlanAssignment)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntityNotFoundException(ClientPlanAssignment.class, id));
    }

    private void fillInClientAndPlan(long clientId, ClientPlanAssignment clientPlanAssignment) {
        Client client = clientsService.getClient(clientId).orElseThrow(() -> new EntityNotFoundException(Client.class, clientId));
        Plan plan = plansService.getPlan(clientPlanAssignment.getPlan().getId()).orElseThrow(() -> new EntityNotFoundException(Plan.class, clientId));
        clientPlanAssignment.setClient(client);
        clientPlanAssignment.setPlan(plan);
    }
}
