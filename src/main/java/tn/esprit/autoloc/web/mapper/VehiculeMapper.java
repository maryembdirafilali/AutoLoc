package tn.esprit.autoloc.web.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.web.dto.VehiculeDTO;

@Component
public class VehiculeMapper {

    public VehiculeDTO toDto(Vehicule entity) {
        if (entity == null) {
            return null;
        }
        List<Long> equipementIds = entity.getEquipements() != null
                ? entity.getEquipements().stream().map(Equipement::getIdEquipement).toList()
                : Collections.emptyList();

        return VehiculeDTO.builder()
                .idVehicule(entity.getIdVehicule())
                .immatriculation(entity.getImmatriculation())
                .marque(entity.getMarque())
                .modele(entity.getModele())
                .categorie(entity.getCategorie())
                .tarifJournalier(entity.getTarifJournalier())
                .statut(entity.getStatut())
                .agenceId(entity.getAgence() != null ? entity.getAgence().getIdAgence() : null)
                .equipementIds(equipementIds)
                .build();
    }

    public Vehicule toEntity(VehiculeDTO dto) {
        if (dto == null) {
            return null;
        }
        Agence agence = null;
        if (dto.getAgenceId() != null) {
            agence = Agence.builder().idAgence(dto.getAgenceId()).build();
        }

        List<Equipement> equipements = new ArrayList<>();
        if (dto.getEquipementIds() != null) {
            equipements = dto.getEquipementIds().stream()
                    .map(id -> Equipement.builder().idEquipement(id).build())
                    .toList();
        }

        return Vehicule.builder()
                .idVehicule(dto.getIdVehicule())
                .immatriculation(dto.getImmatriculation())
                .marque(dto.getMarque())
                .modele(dto.getModele())
                .categorie(dto.getCategorie())
                .tarifJournalier(dto.getTarifJournalier())
                .statut(dto.getStatut())
                .agence(agence)
                .equipements(equipements)
                .build();
    }
}
