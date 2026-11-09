package tn.esprit.autoloc.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.esprit.autoloc.domain.ModePaiement;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaiementDTO {

    private Long idPaiement;

    @NotNull(message = "Le montant du paiement est obligatoire")
    @Positive(message = "Le montant du paiement doit être strictement supérieur à zéro")
    private BigDecimal montant;

    @NotNull(message = "La date du paiement est obligatoire")
    private LocalDate datePaiement;

    @NotNull(message = "Le mode de paiement est obligatoire")
    private ModePaiement modePaiement;

    private Long contratId;

    @JsonProperty("contrat")
    public void setNestedContrat(Map<String, Object> contrat) {
        if (contrat != null) {
            Object id = contrat.get("idContrat") != null ? contrat.get("idContrat") : contrat.get("id");
            if (id instanceof Number number) {
                this.contratId = number.longValue();
            }
        }
    }
}
