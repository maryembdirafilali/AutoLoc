package tn.esprit.autoloc.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.repository.IContratRepository;
import tn.esprit.autoloc.service.IContratService;

@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements IContratService {

    private final IContratRepository contratRepository;

    @Override
    public Contrat saveContrat(Contrat contrat) {
        return contratRepository.save(contrat);
    }

    @Override
    public Contrat updateContrat(Long id, Contrat contrat) {
        return contratRepository.findById(id).map(existingContrat -> {
            existingContrat.setDateSignature(contrat.getDateSignature());
            existingContrat.setMontantTotal(contrat.getMontantTotal());
            existingContrat.setValide(contrat.isValide());
            existingContrat.setReservation(contrat.getReservation());
            return contratRepository.save(existingContrat);
        }).orElseThrow(() -> new RuntimeException("Contrat introuvable avec l'id : " + id));
    }

    @Override
    public Contrat getContratById(Long id) {
        return contratRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contrat introuvable avec l'id : " + id));
    }

    @Override
    public List<Contrat> getAllContrats() {
        return contratRepository.findAll();
    }

    @Override
    public void deleteContrat(Long id) {
        contratRepository.deleteById(id);
    }
}
