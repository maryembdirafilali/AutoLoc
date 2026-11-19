package tn.esprit.autoloc.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.esprit.autoloc.domain.StatutReservation;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationWithContratRequestDTO {

    @NotNull(message = "La date de début de réservation est obligatoire")
    private LocalDate dateDebut;

    @NotNull(message = "La date de fin de réservation est obligatoire")
    private LocalDate dateFin;

    private StatutReservation statut;

    @NotNull(message = "L'identifiant du client est obligatoire")
    private Long clientId;

    @NotNull(message = "L'identifiant du véhicule est obligatoire")
    private Long vehiculeId;

    @NotNull(message = "La date de signature du contrat est obligatoire")
    private LocalDate dateSignature;

    private BigDecimal montantTotal;

    private boolean valide;

    @Valid
    @Builder.Default
    private List<PaiementDTO> paiements = new ArrayList<>();
}
