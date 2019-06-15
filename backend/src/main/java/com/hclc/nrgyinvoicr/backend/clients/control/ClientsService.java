package com.hclc.nrgyinvoicr.backend.clients.control;

import com.hclc.nrgyinvoicr.backend.clients.entity.Client;
import com.hclc.nrgyinvoicr.backend.clients.entity.ClientsSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.hclc.nrgyinvoicr.backend.clients.entity.ClientsSpecification.*;

@Service
public class ClientsService {
    private final ClientsRepository clientsRepository;
    private final ClientNumberGenerator clientNumberGenerator;

    ClientsService(ClientsRepository clientsRepository, ClientNumberGenerator clientNumberGenerator) {
        this.clientsRepository = clientsRepository;
        this.clientNumberGenerator = clientNumberGenerator;
    }

    public Client createClient(Client client) {
        client.setId(null);
        client.setNumber(clientNumberGenerator.generateClientNumber());
        return clientsRepository.save(client);
    }

    public Optional<Client> getClient(Long id) {
        return clientsRepository.findById(id);
    }

    public Page<Client> findClients(ClientsSearchCriteria criteria) {
        Specification<Client> specification = numberLike(criteria.getNumber())
                .and(firstNameLike(criteria.getFirstName()))
                .and(lastNameLike(criteria.getLastName()))
                .and(addressLike(criteria.getAddress()))
                .and(postalCodeLike(criteria.getPostalCode()))
                .and(cityLike(criteria.getCity()));
        return clientsRepository.findAll(specification, criteria.getPageDefinition().asPageRequest());
    }

    public Optional<Client> updateClient(Client client) {
        return clientsRepository
                .findById(client.getId())
                .map(c -> client.withNumber(c.getNumber()))
                .map(clientsRepository::save);
    }
}
