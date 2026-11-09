package tn.esprit.autoloc.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IMaintenanceRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.service.IMaintenanceService;
import tn.esprit.autoloc.web.dto.MaintenanceDTO;
import tn.esprit.autoloc.web.mapper.MaintenanceMapper;

@Service
@RequiredArgsConstructor
public class MaintenanceServiceImpl implements IMaintenanceService {

    private final IMaintenanceRepository maintenanceRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final MaintenanceMapper maintenanceMapper;

    @Override
    public MaintenanceDTO create(MaintenanceDTO maintenanceDTO) {
        if (maintenanceDTO.getIdMaintenance() != null) {
            throw new IllegalArgumentException("Une nouvelle maintenance ne doit pas avoir d'identifiant");
        }
        if (maintenanceDTO.getDateDebut() != null && maintenanceDTO.getDateFin() != null &&
            maintenanceDTO.getDateFin().isBefore(maintenanceDTO.getDateDebut())) {
            throw new IllegalArgumentException("La date de fin de maintenance ne peut pas être antérieure à la date de début");
        }

        Maintenance maintenance = maintenanceMapper.toEntity(maintenanceDTO);

        if (maintenanceDTO.getVehiculeId() != null) {
            Vehicule vehicule = vehiculeRepository.findById(maintenanceDTO.getVehiculeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Vehicule", maintenanceDTO.getVehiculeId()));
            maintenance.setVehicule(vehicule);
        }

        Maintenance saved = maintenanceRepository.save(maintenance);
        return maintenanceMapper.toDto(saved);
    }

    @Override
    public MaintenanceDTO findById(Long id) {
        Maintenance maintenance = maintenanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance", id));
        return maintenanceMapper.toDto(maintenance);
    }

    @Override
    public List<MaintenanceDTO> findAll() {
        return maintenanceRepository.findAll().stream()
                .map(maintenanceMapper::toDto)
                .toList();
    }

    @Override
    public MaintenanceDTO update(Long id, MaintenanceDTO maintenanceDTO) {
        Maintenance existant = maintenanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance", id));

        if (maintenanceDTO.getDateDebut() != null && maintenanceDTO.getDateFin() != null &&
            maintenanceDTO.getDateFin().isBefore(maintenanceDTO.getDateDebut())) {
            throw new IllegalArgumentException("La date de fin de maintenance ne peut pas être antérieure à la date de début");
        }

        if (maintenanceDTO.getVehiculeId() != null) {
            Vehicule vehicule = vehiculeRepository.findById(maintenanceDTO.getVehiculeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Vehicule", maintenanceDTO.getVehiculeId()));
            existant.setVehicule(vehicule);
        } else {
            existant.setVehicule(null);
        }

        existant.setDateDebut(maintenanceDTO.getDateDebut());
        existant.setDateFin(maintenanceDTO.getDateFin());
        existant.setDescription(maintenanceDTO.getDescription());

        Maintenance updated = maintenanceRepository.save(existant);
        return maintenanceMapper.toDto(updated);
    }

    @Override
    public void deleteById(Long id) {
        if (!maintenanceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Maintenance", id);
        }
        maintenanceRepository.deleteById(id);
    }
}
