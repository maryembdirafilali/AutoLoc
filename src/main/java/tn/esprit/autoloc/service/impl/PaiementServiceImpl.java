package tn.esprit.autoloc.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.repository.IPaiementRepository;
import tn.esprit.autoloc.service.IPaiementService;

@Service
@RequiredArgsConstructor
public class PaiementServiceImpl implements IPaiementService {

    private final IPaiementRepository paiementRepository;

    @Override
    public Paiement savePaiement(Paiement paiement) {
        return paiementRepository.save(paiement);
    }

    @Override
    public Paiement updatePaiement(Long id, Paiement paiement) {
        return paiementRepository.findById(id).map(existingPaiement -> {
            existingPaiement.setMontant(paiement.getMontant());
            existingPaiement.setDatePaiement(paiement.getDatePaiement());
            existingPaiement.setModePaiement(paiement.getModePaiement());
            existingPaiement.setContrat(paiement.getContrat());
            return paiementRepository.save(existingPaiement);
        }).orElseThrow(() -> new RuntimeException("Paiement introuvable avec l'id : " + id));
    }

    @Override
    public Paiement getPaiementById(Long id) {
        return paiementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paiement introuvable avec l'id : " + id));
    }

    @Override
    public List<Paiement> getAllPaiements() {
        return paiementRepository.findAll();
    }

    @Override
    public void deletePaiement(Long id) {
        paiementRepository.deleteById(id);
    }
}
