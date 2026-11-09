package tn.esprit.autoloc.web.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.web.dto.MaintenanceDTO;

@Component
public class MaintenanceMapper {

    public MaintenanceDTO toDto(Maintenance entity) {
        if (entity == null) {
            return null;
        }
        return MaintenanceDTO.builder()
                .idMaintenance(entity.getIdMaintenance())
                .dateDebut(entity.getDateDebut())
                .dateFin(entity.getDateFin())
                .description(entity.getDescription())
                .vehiculeId(entity.getVehicule() != null ? entity.getVehicule().getIdVehicule() : null)
                .build();
    }

    public Maintenance toEntity(MaintenanceDTO dto) {
        if (dto == null) {
            return null;
        }
        Vehicule vehicule = null;
        if (dto.getVehiculeId() != null) {
            vehicule = Vehicule.builder().idVehicule(dto.getVehiculeId()).build();
        }

        return Maintenance.builder()
                .idMaintenance(dto.getIdMaintenance())
                .dateDebut(dto.getDateDebut())
                .dateFin(dto.getDateFin())
                .description(dto.getDescription())
                .vehicule(vehicule)
                .build();
    }
}
