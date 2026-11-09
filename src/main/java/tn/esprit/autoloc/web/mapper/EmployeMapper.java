package tn.esprit.autoloc.web.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.web.dto.EmployeDTO;

@Component
public class EmployeMapper {

    public EmployeDTO toDto(Employe entity) {
        if (entity == null) {
            return null;
        }
        return EmployeDTO.builder()
                .idEmploye(entity.getIdEmploye())
                .nom(entity.getNom())
                .prenom(entity.getPrenom())
                .role(entity.getRole())
                .agenceId(entity.getAgence() != null ? entity.getAgence().getIdAgence() : null)
                .build();
    }

    public Employe toEntity(EmployeDTO dto) {
        if (dto == null) {
            return null;
        }
        Agence agence = null;
        if (dto.getAgenceId() != null) {
            agence = Agence.builder().idAgence(dto.getAgenceId()).build();
        }

        return Employe.builder()
                .idEmploye(dto.getIdEmploye())
                .nom(dto.getNom())
                .prenom(dto.getPrenom())
                .role(dto.getRole())
                .agence(agence)
                .build();
    }
}
