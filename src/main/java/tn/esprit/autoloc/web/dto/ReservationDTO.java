package tn.esprit.autoloc.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.esprit.autoloc.domain.StatutReservation;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDTO {

    private Long idReservation;

    @NotNull(message = "La date de début est obligatoire")
    private LocalDate dateDebut;

    @NotNull(message = "La date de fin est obligatoire")
    private LocalDate dateFin;

    private StatutReservation statut;

    @NotNull(message = "L'identifiant du client est obligatoire")
    private Long clientId;

    @NotNull(message = "L'identifiant du véhicule est obligatoire")
    private Long vehiculeId;

    private Long contratId;

    @JsonProperty("client")
    public void setNestedClient(Map<String, Object> client) {
        if (client != null) {
            Object id = client.get("idClient") != null ? client.get("idClient") : client.get("id");
            if (id instanceof Number number) {
                this.clientId = number.longValue();
            }
        }
    }

    @JsonProperty("vehicule")
    public void setNestedVehicule(Map<String, Object> vehicule) {
        if (vehicule != null) {
            Object id = vehicule.get("idVehicule") != null ? vehicule.get("idVehicule") : vehicule.get("id");
            if (id instanceof Number number) {
                this.vehiculeId = number.longValue();
            }
        }
    }
}
