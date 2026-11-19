package tn.esprit.autoloc.web.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.esprit.autoloc.domain.StatutReservation;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationResponseDTO {

    private Long idReservation;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private StatutReservation statut;

    private Long clientId;
    private String clientNom;
    private String clientPrenom;
    private String clientEmail;

    private Long vehiculeId;
    private String vehiculeImmatriculation;
    private String vehiculeMarque;
    private String vehiculeModele;

    private Long contratId;
    private ContratDTO contrat;
}
