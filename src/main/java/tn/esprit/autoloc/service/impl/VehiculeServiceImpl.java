package tn.esprit.autoloc.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.service.IVehiculeService;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    private final IVehiculeRepository vehiculeRepository;

    @Override
    public Vehicule saveVehicule(Vehicule vehicule) {
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public Vehicule updateVehicule(Long id, Vehicule vehicule) {
        return vehiculeRepository.findById(id).map(existingVehicule -> {
            existingVehicule.setImmatriculation(vehicule.getImmatriculation());
            existingVehicule.setMarque(vehicule.getMarque());
            existingVehicule.setModele(vehicule.getModele());
            existingVehicule.setCategorie(vehicule.getCategorie());
            existingVehicule.setTarifJournalier(vehicule.getTarifJournalier());
            existingVehicule.setStatut(vehicule.getStatut());
            existingVehicule.setAgence(vehicule.getAgence());
            existingVehicule.setEquipements(vehicule.getEquipements());
            return vehiculeRepository.save(existingVehicule);
        }).orElseThrow(() -> new RuntimeException("Vehicule introuvable avec l'id : " + id));
    }

    @Override
    public Vehicule getVehiculeById(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicule introuvable avec l'id : " + id));
    }

    @Override
    public List<Vehicule> getAllVehicules() {
        return vehiculeRepository.findAll();
    }

    @Override
    public void deleteVehicule(Long id) {
        vehiculeRepository.deleteById(id);
    }
}
