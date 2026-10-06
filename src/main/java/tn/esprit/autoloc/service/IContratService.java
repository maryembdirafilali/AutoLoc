package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.domain.Contrat;

public interface IContratService {
    Contrat saveContrat(Contrat contrat);
    Contrat updateContrat(Long id, Contrat contrat);
    Contrat getContratById(Long id);
    List<Contrat> getAllContrats();
    void deleteContrat(Long id);
}
