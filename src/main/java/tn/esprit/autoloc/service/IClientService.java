package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.domain.Client;

public interface IClientService {
    Client saveClient(Client client);
    Client updateClient(Long id, Client client);
    Client getClientById(Long id);
    List<Client> getAllClients();
    void deleteClient(Long id);
}
