package tn.esprit.autoloc.web.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.web.dto.AgenceDTO;

@Component
public class AgenceMapper {

    public AgenceDTO toDto(Agence entity) {
        if (entity == null) {
            return null;
        }
        return AgenceDTO.builder()
                .idAgence(entity.getIdAgence())
                .nom(entity.getNom())
                .ville(entity.getVille())
                .adresse(entity.getAdresse())
                .telephone(entity.getTelephone())
                .build();
    }

    public Agence toEntity(AgenceDTO dto) {
        if (dto == null) {
            return null;
        }
        return Agence.builder()
                .idAgence(dto.getIdAgence())
                .nom(dto.getNom())
                .ville(dto.getVille())
                .adresse(dto.getAdresse())
                .telephone(dto.getTelephone())
                .build();
    }
}
