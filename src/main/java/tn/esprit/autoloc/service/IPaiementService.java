package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.domain.Paiement;

public interface IPaiementService {
    Paiement savePaiement(Paiement paiement);
    Paiement updatePaiement(Long id, Paiement paiement);
    Paiement getPaiementById(Long id);
    List<Paiement> getAllPaiements();
    void deletePaiement(Long id);
}
