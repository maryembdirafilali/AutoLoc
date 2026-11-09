package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.web.dto.VehiculeDTO;

public interface IVehiculeService {
    VehiculeDTO create(VehiculeDTO vehiculeDTO);
    VehiculeDTO findById(Long id);
    List<VehiculeDTO> findAll();
    VehiculeDTO update(Long id, VehiculeDTO vehiculeDTO);
    void deleteById(Long id);
}
