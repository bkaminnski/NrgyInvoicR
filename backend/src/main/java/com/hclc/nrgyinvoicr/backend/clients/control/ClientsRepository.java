package com.hclc.nrgyinvoicr.backend.clients.control;

import com.hclc.nrgyinvoicr.backend.clients.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.Nullable;

interface ClientsRepository extends JpaRepository<Client, Long>, JpaSpecificationExecutor<Client> {

    @Override
    @EntityGraph("clientWithMeter")
    Page<Client> findAll(@Nullable Specification<Client> spec, Pageable pageable);
}
