package tn.esprit.autoloc.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.repository.IMaintenanceRepository;
import tn.esprit.autoloc.service.IMaintenanceService;

@Service
@RequiredArgsConstructor
public class MaintenanceServiceImpl implements IMaintenanceService {

    private final IMaintenanceRepository maintenanceRepository;

    @Override
    public Maintenance saveMaintenance(Maintenance maintenance) {
        return maintenanceRepository.save(maintenance);
    }

    @Override
    public Maintenance updateMaintenance(Long id, Maintenance maintenance) {
        return maintenanceRepository.findById(id).map(existingMaintenance -> {
            existingMaintenance.setDateDebut(maintenance.getDateDebut());
            existingMaintenance.setDateFin(maintenance.getDateFin());
            existingMaintenance.setDescription(maintenance.getDescription());
            existingMaintenance.setVehicule(maintenance.getVehicule());
            return maintenanceRepository.save(existingMaintenance);
        }).orElseThrow(() -> new RuntimeException("Maintenance introuvable avec l'id : " + id));
    }

    @Override
    public Maintenance getMaintenanceById(Long id) {
        return maintenanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Maintenance introuvable avec l'id : " + id));
    }

    @Override
    public List<Maintenance> getAllMaintenances() {
        return maintenanceRepository.findAll();
    }

    @Override
    public void deleteMaintenance(Long id) {
        maintenanceRepository.deleteById(id);
    }
}
