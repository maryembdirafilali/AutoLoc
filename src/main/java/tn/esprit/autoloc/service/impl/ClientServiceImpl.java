package tn.esprit.autoloc.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.repository.IClientRepository;
import tn.esprit.autoloc.service.IClientService;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientService {

    private final IClientRepository clientRepository;

    @Override
    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Client updateClient(Long id, Client client) {
        return clientRepository.findById(id).map(existingClient -> {
            existingClient.setNom(client.getNom());
            existingClient.setPrenom(client.getPrenom());
            existingClient.setEmail(client.getEmail());
            existingClient.setTelephone(client.getTelephone());
            existingClient.setNumPermis(client.getNumPermis());
            existingClient.setDateInscription(client.getDateInscription());
            return clientRepository.save(existingClient);
        }).orElseThrow(() -> new RuntimeException("Client introuvable avec l'id : " + id));
    }

    @Override
    public Client getClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client introuvable avec l'id : " + id));
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}
