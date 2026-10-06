package tn.esprit.autoloc.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.repository.IEquipementRepository;
import tn.esprit.autoloc.service.IEquipementService;

@Service
@RequiredArgsConstructor
public class EquipementServiceImpl implements IEquipementService {

    private final IEquipementRepository equipementRepository;

    @Override
    public Equipement saveEquipement(Equipement equipement) {
        return equipementRepository.save(equipement);
    }

    @Override
    public Equipement updateEquipement(Long id, Equipement equipement) {
        return equipementRepository.findById(id).map(existingEquipement -> {
            existingEquipement.setLibelle(equipement.getLibelle());
            return equipementRepository.save(existingEquipement);
        }).orElseThrow(() -> new RuntimeException("Equipement introuvable avec l'id : " + id));
    }

    @Override
    public Equipement getEquipementById(Long id) {
        return equipementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipement introuvable avec l'id : " + id));
    }

    @Override
    public List<Equipement> getAllEquipements() {
        return equipementRepository.findAll();
    }

    @Override
    public void deleteEquipement(Long id) {
        equipementRepository.deleteById(id);
    }
}
