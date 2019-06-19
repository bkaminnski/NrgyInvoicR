package com.hclc.nrgyinvoicr.backend.clients.control;

import com.hclc.nrgyinvoicr.backend.clients.entity.Client;
import com.hclc.nrgyinvoicr.backend.clients.entity.ClientsSearchCriteria;
import com.hclc.nrgyinvoicr.backend.meters.control.MetersService;
import com.hclc.nrgyinvoicr.backend.meters.entity.Meter;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.hclc.nrgyinvoicr.backend.clients.entity.ClientsSpecification.*;

@Service
public class ClientsService {
    private final ClientsRepository clientsRepository;
    private final ClientNumberGenerator clientNumberGenerator;
    private final MetersService metersService;

    ClientsService(ClientsRepository clientsRepository, ClientNumberGenerator clientNumberGenerator, MetersService metersService) {
        this.clientsRepository = clientsRepository;
        this.clientNumberGenerator = clientNumberGenerator;
        this.metersService = metersService;
    }

    public Client createClient(Client client) {
        Meter meter = getMeterFor(client);
        client.setId(null);
        client.setNumber(clientNumberGenerator.generateClientNumber());
        client.setMeter(meter);
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
        Meter meter = getMeterFor(client);
        return clientsRepository
                .findById(client.getId())
                .map(c -> client.withNumber(c.getNumber()).withMeter(meter))
                .map(clientsRepository::save);
    }

    private Meter getMeterFor(Client client) {
        return metersService
                .getMeter(client.getMeter().getId())
                .orElseThrow(() -> new RuntimeException("Meter does not exist; id: " + client.getMeter().getId()));
    }
}
