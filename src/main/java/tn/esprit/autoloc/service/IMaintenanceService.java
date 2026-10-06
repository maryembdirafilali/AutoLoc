package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.domain.Maintenance;

public interface IMaintenanceService {
    Maintenance saveMaintenance(Maintenance maintenance);
    Maintenance updateMaintenance(Long id, Maintenance maintenance);
    Maintenance getMaintenanceById(Long id);
    List<Maintenance> getAllMaintenances();
    void deleteMaintenance(Long id);
}
