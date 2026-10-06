package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.domain.Vehicule;

public interface IVehiculeService {
    Vehicule saveVehicule(Vehicule vehicule);
    Vehicule updateVehicule(Long id, Vehicule vehicule);
    Vehicule getVehiculeById(Long id);
    List<Vehicule> getAllVehicules();
    void deleteVehicule(Long id);
}
