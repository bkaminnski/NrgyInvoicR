package com.softwarewithpassion.nrgyinvoicr.backend.clients.control;

import com.softwarewithpassion.nrgyinvoicr.backend.clients.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.Nullable;

import java.util.List;

public interface ClientsRepository extends JpaRepository<Client, Long>, JpaSpecificationExecutor<Client> {

    @Override
    @EntityGraph("clientWithMeter")
    Page<Client> findAll(@Nullable Specification<Client> spec, Pageable pageable);

    @Override
    @EntityGraph("clientWithMeter")
    List<Client> findAll();
}
