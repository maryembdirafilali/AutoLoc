package tn.esprit.autoloc.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.repository.IEquipementRepository;
import tn.esprit.autoloc.repository.IMaintenanceRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.service.IVehiculeService;
import tn.esprit.autoloc.web.dto.VehiculeDTO;
import tn.esprit.autoloc.web.mapper.VehiculeMapper;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    private final IVehiculeRepository vehiculeRepository;
    private final IAgenceRepository agenceRepository;
    private final IEquipementRepository equipementRepository;
    private final IMaintenanceRepository maintenanceRepository;
    private final VehiculeMapper vehiculeMapper;

    @Override
    public VehiculeDTO create(VehiculeDTO vehiculeDTO) {
        if (vehiculeDTO.getIdVehicule() != null) {
            throw new IllegalArgumentException("Un nouveau véhicule ne doit pas avoir d'identifiant");
        }
        if (vehiculeDTO.getTarifJournalier() != null && vehiculeDTO.getTarifJournalier().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Le tarif journalier d'un véhicule ne peut pas être négatif");
        }

        Vehicule vehicule = vehiculeMapper.toEntity(vehiculeDTO);

        if (vehiculeDTO.getAgenceId() != null) {
            Agence agence = agenceRepository.findById(vehiculeDTO.getAgenceId())
                    .orElseThrow(() -> new ResourceNotFoundException("Agence", vehiculeDTO.getAgenceId()));
            vehicule.setAgence(agence);
        }

        if (vehiculeDTO.getEquipementIds() != null && !vehiculeDTO.getEquipementIds().isEmpty()) {
            List<Equipement> equipements = equipementRepository.findAllById(vehiculeDTO.getEquipementIds());
            vehicule.setEquipements(equipements);
        }

        Vehicule saved = vehiculeRepository.save(vehicule);
        return vehiculeMapper.toDto(saved);
    }

    @Override
    public VehiculeDTO findById(Long id) {
        Vehicule vehicule = vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
        return vehiculeMapper.toDto(vehicule);
    }

    @Override
    public List<VehiculeDTO> findAll() {
        return vehiculeRepository.findAll().stream()
                .map(vehiculeMapper::toDto)
                .toList();
    }

    @Override
    public VehiculeDTO update(Long id, VehiculeDTO vehiculeDTO) {
        Vehicule existant = vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));

        if (vehiculeDTO.getTarifJournalier() != null && vehiculeDTO.getTarifJournalier().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Le tarif journalier d'un véhicule ne peut pas être négatif");
        }

        existant.setImmatriculation(vehiculeDTO.getImmatriculation());
        existant.setMarque(vehiculeDTO.getMarque());
        existant.setModele(vehiculeDTO.getModele());
        existant.setCategorie(vehiculeDTO.getCategorie());
        existant.setTarifJournalier(vehiculeDTO.getTarifJournalier());
        existant.setStatut(vehiculeDTO.getStatut());

        if (vehiculeDTO.getAgenceId() != null) {
            Agence agence = agenceRepository.findById(vehiculeDTO.getAgenceId())
                    .orElseThrow(() -> new ResourceNotFoundException("Agence", vehiculeDTO.getAgenceId()));
            existant.setAgence(agence);
        } else {
            existant.setAgence(null);
        }

        if (vehiculeDTO.getEquipementIds() != null) {
            List<Equipement> equipements = equipementRepository.findAllById(vehiculeDTO.getEquipementIds());
            existant.setEquipements(equipements);
        } else {
            existant.setEquipements(new ArrayList<>());
        }

        Vehicule updated = vehiculeRepository.save(existant);
        return vehiculeMapper.toDto(updated);
    }

    @Override
    public void deleteById(Long id) {
        if (!vehiculeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicule", id);
        }
        vehiculeRepository.deleteById(id);
    }

    @Override
    public VehiculeDTO affecterVehiculeAEquipement(Long idVehicule, Long idEquipement) {
        Vehicule vehicule = vehiculeRepository.findById(idVehicule)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", idVehicule));
        Equipement equipement = equipementRepository.findById(idEquipement)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", idEquipement));

        if (!vehicule.getEquipements().contains(equipement)) {
            vehicule.getEquipements().add(equipement);
        }

        Vehicule saved = vehiculeRepository.save(vehicule);
        return vehiculeMapper.toDto(saved);
    }

    @Override
    public VehiculeDTO affecterMaintenanceAvehicule(String description, String immatriculation) {
        Vehicule vehicule = vehiculeRepository.findByImmatriculation(immatriculation)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule avec immatriculation '" + immatriculation + "' introuvable"));

        Maintenance maintenance = maintenanceRepository.findFirstByDescription(description)
                .orElseGet(() -> Maintenance.builder()
                        .description(description)
                        .dateDebut(LocalDate.now())
                        .build());

        maintenance.setVehicule(vehicule);
        maintenanceRepository.save(maintenance);

        if (!vehicule.getMaintenances().contains(maintenance)) {
            vehicule.getMaintenances().add(maintenance);
        }

        Vehicule saved = vehiculeRepository.save(vehicule);
        return vehiculeMapper.toDto(saved);
    }
}
