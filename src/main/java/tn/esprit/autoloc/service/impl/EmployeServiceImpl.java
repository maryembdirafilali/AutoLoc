package tn.esprit.autoloc.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.repository.IEmployeRepository;
import tn.esprit.autoloc.service.IEmployeService;
import tn.esprit.autoloc.web.dto.EmployeDTO;
import tn.esprit.autoloc.web.mapper.EmployeMapper;

@Service
@RequiredArgsConstructor
public class EmployeServiceImpl implements IEmployeService {

    private final IEmployeRepository employeRepository;
    private final IAgenceRepository agenceRepository;
    private final EmployeMapper employeMapper;

    @Override
    public EmployeDTO create(EmployeDTO employeDTO) {
        if (employeDTO.getIdEmploye() != null) {
            throw new IllegalArgumentException("Un nouvel employé ne doit pas avoir d'identifiant");
        }

        Employe employe = employeMapper.toEntity(employeDTO);

        if (employeDTO.getAgenceId() != null) {
            Agence agence = agenceRepository.findById(employeDTO.getAgenceId())
                    .orElseThrow(() -> new ResourceNotFoundException("Agence", employeDTO.getAgenceId()));
            employe.setAgence(agence);
        }

        Employe saved = employeRepository.save(employe);
        return employeMapper.toDto(saved);
    }

    @Override
    public EmployeDTO findById(Long id) {
        Employe employe = employeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employe", id));
        return employeMapper.toDto(employe);
    }

    @Override
    public List<EmployeDTO> findAll() {
        return employeRepository.findAll().stream()
                .map(employeMapper::toDto)
                .toList();
    }

    @Override
    public EmployeDTO update(Long id, EmployeDTO employeDTO) {
        Employe existant = employeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employe", id));

        existant.setNom(employeDTO.getNom());
        existant.setPrenom(employeDTO.getPrenom());
        existant.setRole(employeDTO.getRole());

        if (employeDTO.getAgenceId() != null) {
            Agence agence = agenceRepository.findById(employeDTO.getAgenceId())
                    .orElseThrow(() -> new ResourceNotFoundException("Agence", employeDTO.getAgenceId()));
            existant.setAgence(agence);
        } else {
            existant.setAgence(null);
        }

        Employe updated = employeRepository.save(existant);
        return employeMapper.toDto(updated);
    }

    @Override
    public void deleteById(Long id) {
        if (!employeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employe", id);
        }
        employeRepository.deleteById(id);
    }
}
