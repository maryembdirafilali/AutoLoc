package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.web.dto.ClientDTO;

public interface IClientService {
    ClientDTO create(ClientDTO clientDTO);
    ClientDTO findById(Long id);
    List<ClientDTO> findAll();
    ClientDTO update(Long id, ClientDTO clientDTO);
    void deleteById(Long id);
}
