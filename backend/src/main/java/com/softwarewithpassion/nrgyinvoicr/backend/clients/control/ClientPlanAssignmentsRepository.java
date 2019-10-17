package com.softwarewithpassion.nrgyinvoicr.backend.clients.control;

import com.softwarewithpassion.nrgyinvoicr.backend.clients.entity.ClientPlanAssignment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.ZonedDateTime;
import java.util.Optional;

public interface ClientPlanAssignmentsRepository extends JpaRepository<ClientPlanAssignment, Long>, JpaSpecificationExecutor<ClientPlanAssignment> {

    Page<ClientPlanAssignment> findByClientId(Long clientId, Pageable pageable);

    @EntityGraph("clientPlanAssignmentWithClientAndPlan")
    Optional<ClientPlanAssignment> findFirstByClientIdAndValidSinceLessThanEqualOrderByValidSinceAscIdDesc(Long clientId, ZonedDateTime validOnDate);
}
