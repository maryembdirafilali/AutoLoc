package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.domain.Agence;

public interface IAgenceService {
    Agence saveAgence(Agence agence);
    Agence updateAgence(Long id, Agence agence);
    Agence getAgenceById(Long id);
    List<Agence> getAllAgences();
    void deleteAgence(Long id);
}
