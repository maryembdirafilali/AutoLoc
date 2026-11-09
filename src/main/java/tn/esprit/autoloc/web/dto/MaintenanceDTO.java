package tn.esprit.autoloc.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaintenanceDTO {

    private Long idMaintenance;

    @NotNull(message = "La date de début est obligatoire")
    private LocalDate dateDebut;

    @NotNull(message = "La date de fin est obligatoire")
    private LocalDate dateFin;

    private String description;

    private Long vehiculeId;

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
