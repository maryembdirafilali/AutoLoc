package tn.esprit.autoloc.web.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.web.dto.PaiementDTO;

@Component
public class PaiementMapper {

    public PaiementDTO toDto(Paiement entity) {
        if (entity == null) {
            return null;
        }
        return PaiementDTO.builder()
                .idPaiement(entity.getIdPaiement())
                .montant(entity.getMontant())
                .datePaiement(entity.getDatePaiement())
                .modePaiement(entity.getModePaiement())
                .contratId(entity.getContrat() != null ? entity.getContrat().getIdContrat() : null)
                .build();
    }

    public Paiement toEntity(PaiementDTO dto) {
        if (dto == null) {
            return null;
        }
        Contrat contrat = null;
        if (dto.getContratId() != null) {
            contrat = Contrat.builder().idContrat(dto.getContratId()).build();
        }
        return Paiement.builder()
                .idPaiement(dto.getIdPaiement())
                .montant(dto.getMontant())
                .datePaiement(dto.getDatePaiement())
                .modePaiement(dto.getModePaiement())
                .contrat(contrat)
                .build();
    }
}
