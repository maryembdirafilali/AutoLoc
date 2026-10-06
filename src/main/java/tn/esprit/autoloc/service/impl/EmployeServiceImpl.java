package tn.esprit.autoloc.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.repository.IEmployeRepository;
import tn.esprit.autoloc.service.IEmployeService;

@Service
@RequiredArgsConstructor
public class EmployeServiceImpl implements IEmployeService {

    private final IEmployeRepository employeRepository;

    @Override
    public Employe saveEmploye(Employe employe) {
        return employeRepository.save(employe);
    }

    @Override
    public Employe updateEmploye(Long id, Employe employe) {
        return employeRepository.findById(id).map(existingEmploye -> {
            existingEmploye.setNom(employe.getNom());
            existingEmploye.setPrenom(employe.getPrenom());
            existingEmploye.setRole(employe.getRole());
            existingEmploye.setAgence(employe.getAgence());
            return employeRepository.save(existingEmploye);
        }).orElseThrow(() -> new RuntimeException("Employe introuvable avec l'id : " + id));
    }

    @Override
    public Employe getEmployeById(Long id) {
        return employeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employe introuvable avec l'id : " + id));
    }

    @Override
    public List<Employe> getAllEmployes() {
        return employeRepository.findAll();
    }

    @Override
    public void deleteEmploye(Long id) {
        employeRepository.deleteById(id);
    }
}
