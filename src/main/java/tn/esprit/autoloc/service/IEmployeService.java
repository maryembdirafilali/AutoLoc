package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.domain.Employe;

public interface IEmployeService {
    Employe saveEmploye(Employe employe);
    Employe updateEmploye(Long id, Employe employe);
    Employe getEmployeById(Long id);
    List<Employe> getAllEmployes();
    void deleteEmploye(Long id);
}
