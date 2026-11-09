package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.web.dto.MaintenanceDTO;

public interface IMaintenanceService {
    MaintenanceDTO create(MaintenanceDTO maintenanceDTO);
    MaintenanceDTO findById(Long id);
    List<MaintenanceDTO> findAll();
    MaintenanceDTO update(Long id, MaintenanceDTO maintenanceDTO);
    void deleteById(Long id);
}
