package tn.esprit.autoloc.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContratDTO {

    private Long idContrat;

    @NotNull(message = "La date de signature est obligatoire")
    private LocalDate dateSignature;

    @NotNull(message = "Le montant total est obligatoire")
    @Positive(message = "Le montant total doit être strictement supérieur à zéro")
    private BigDecimal montantTotal;

    private boolean valide;

    private Long reservationId;

    @Valid
    @Builder.Default
    private List<PaiementDTO> paiements = new ArrayList<>();

    @JsonProperty("reservation")
    public void setNestedReservation(Map<String, Object> reservation) {
        if (reservation != null) {
            Object id = reservation.get("idReservation") != null ? reservation.get("idReservation") : reservation.get("id");
            if (id instanceof Number number) {
                this.reservationId = number.longValue();
            }
        }
    }
}
