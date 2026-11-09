package tn.esprit.autoloc.web.mapper;

import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.web.dto.ContratDTO;
import tn.esprit.autoloc.web.dto.PaiementDTO;

@Component
@RequiredArgsConstructor
public class ContratMapper {

    private final PaiementMapper paiementMapper;

    public ContratDTO toDto(Contrat entity) {
        if (entity == null) {
            return null;
        }
        List<PaiementDTO> paiementsDto = entity.getPaiements() != null
                ? entity.getPaiements().stream().map(paiementMapper::toDto).toList()
                : Collections.emptyList();

        return ContratDTO.builder()
                .idContrat(entity.getIdContrat())
                .dateSignature(entity.getDateSignature())
                .montantTotal(entity.getMontantTotal())
                .valide(entity.isValide())
                .reservationId(entity.getReservation() != null ? entity.getReservation().getIdReservation() : null)
                .paiements(paiementsDto)
                .build();
    }

    public Contrat toEntity(ContratDTO dto) {
        if (dto == null) {
            return null;
        }
        Reservation reservation = null;
        if (dto.getReservationId() != null) {
            reservation = Reservation.builder().idReservation(dto.getReservationId()).build();
        }

        Contrat contrat = Contrat.builder()
                .idContrat(dto.getIdContrat())
                .dateSignature(dto.getDateSignature())
                .montantTotal(dto.getMontantTotal())
                .valide(dto.isValide())
                .reservation(reservation)
                .build();

        if (dto.getPaiements() != null) {
            List<Paiement> paiements = dto.getPaiements().stream()
                    .map(pDto -> {
                        Paiement p = paiementMapper.toEntity(pDto);
                        p.setContrat(contrat);
                        return p;
                    })
                    .toList();
            contrat.setPaiements(paiements);
        }

        return contrat;
    }
}
