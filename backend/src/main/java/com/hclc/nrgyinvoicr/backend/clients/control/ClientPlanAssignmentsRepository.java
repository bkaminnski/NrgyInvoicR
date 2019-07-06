package com.hclc.nrgyinvoicr.backend.clients.control;

import com.hclc.nrgyinvoicr.backend.clients.entity.ClientPlanAssignment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

interface ClientPlanAssignmentsRepository extends JpaRepository<ClientPlanAssignment, Long>, JpaSpecificationExecutor<ClientPlanAssignment> {

    Page<ClientPlanAssignment> findByClientId(Long clientId, Pageable pageable);
}
