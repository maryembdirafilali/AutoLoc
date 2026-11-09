package tn.esprit.autoloc.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.esprit.autoloc.domain.RoleEmploye;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeDTO {

    private Long idEmploye;

    @NotBlank(message = "Le nom de l'employé est obligatoire")
    private String nom;

    private String prenom;

    @NotNull(message = "Le rôle de l'employé est obligatoire")
    private RoleEmploye role;

    private Long agenceId;

    @JsonProperty("agence")
    public void setNestedAgence(Map<String, Object> agence) {
        if (agence != null) {
            Object id = agence.get("idAgence") != null ? agence.get("idAgence") : agence.get("id");
            if (id instanceof Number number) {
                this.agenceId = number.longValue();
            }
        }
    }
}
