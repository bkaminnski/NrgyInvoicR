package com.softwarewithpassion.nrgyinvoicr.backend.clients.control;

import com.softwarewithpassion.nrgyinvoicr.backend.clients.entity.Client;
import com.softwarewithpassion.nrgyinvoicr.backend.clients.entity.ClientPlanAssignment;
import com.softwarewithpassion.nrgyinvoicr.backend.clients.entity.ClientPlanAssignmentsSearchCriteria;
import com.softwarewithpassion.nrgyinvoicr.backend.plans.entity.Plan;
import com.softwarewithpassion.nrgyinvoicr.backend.plans.entity.PlanVersion;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class ClientPlanAssignmentsService {
    private final ClientPlanAssignmentsRepository clientPlanAssignmentsRepository;

    public ClientPlanAssignmentsService(ClientPlanAssignmentsRepository clientPlanAssignmentsRepository) {
        this.clientPlanAssignmentsRepository = clientPlanAssignmentsRepository;
    }

    public ClientPlanAssignment createClientPlanAssignment(ClientPlanAssignment clientPlanAssignment) {
        clientPlanAssignment.setId(null);
        return clientPlanAssignmentsRepository.save(clientPlanAssignment);
    }

    public Page<ClientPlanAssignment> findClientPlanAssignments(long clientId, ClientPlanAssignmentsSearchCriteria criteria) {
        return clientPlanAssignmentsRepository.findByClientId(clientId, criteria.getPageDefinition().asPageRequest());
    }

    public PlanVersion findPlanVersion(Client client, ZonedDateTime onDate) throws NoPlanValidOnDate, NoPlanVersionValidOnDate {
        ClientPlanAssignment clientPlanAssignment = clientPlanAssignmentsRepository
                .findFirstByClientIdAndValidSinceLessThanEqualOrderByValidSinceAscIdDesc(client.getId(), onDate)
                .orElseThrow(() -> new NoPlanValidOnDate(client, onDate));
        Plan plan = clientPlanAssignment.getPlan();
        return plan
                .getVersionValidOn(onDate)
                .orElseThrow(() -> new NoPlanVersionValidOnDate(client, plan, onDate));
    }

    public Optional<ClientPlanAssignment> updateClientPlanAssignment(ClientPlanAssignment clientPlanAssignment) {
        return clientPlanAssignmentsRepository
                .findById(clientPlanAssignment.getId())
                .map(p -> clientPlanAssignmentsRepository.save(clientPlanAssignment));
    }
}
