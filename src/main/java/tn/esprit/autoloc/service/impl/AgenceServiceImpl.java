package tn.esprit.autoloc.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.service.IAgenceService;

@Service
@RequiredArgsConstructor
public class AgenceServiceImpl implements IAgenceService {

    private final IAgenceRepository agenceRepository;

    @Override
    public Agence saveAgence(Agence agence) {
        return agenceRepository.save(agence);
    }

    @Override
    public Agence updateAgence(Long id, Agence agence) {
        return agenceRepository.findById(id).map(existingAgence -> {
            existingAgence.setNom(agence.getNom());
            existingAgence.setVille(agence.getVille());
            existingAgence.setAdresse(agence.getAdresse());
            existingAgence.setTelephone(agence.getTelephone());
            return agenceRepository.save(existingAgence);
        }).orElseThrow(() -> new RuntimeException("Agence introuvable avec l'id : " + id));
    }

    @Override
    public Agence getAgenceById(Long id) {
        return agenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agence introuvable avec l'id : " + id));
    }

    @Override
    public List<Agence> getAllAgences() {
        return agenceRepository.findAll();
    }

    @Override
    public void deleteAgence(Long id) {
        agenceRepository.deleteById(id);
    }
}
