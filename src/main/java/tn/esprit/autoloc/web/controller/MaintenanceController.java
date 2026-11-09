package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.autoloc.service.IMaintenanceService;
import tn.esprit.autoloc.web.dto.MaintenanceDTO;

@Tag(name = "Maintenances", description = "Gestion des entretiens et réparations de véhicules")
@RestController
@RequestMapping("/maintenances")
@RequiredArgsConstructor
public class MaintenanceController {

    private final IMaintenanceService maintenanceService;

    @Operation(summary = "Lister toutes les opérations de maintenance")
    @ApiResponse(responseCode = "200", description = "Liste des opérations de maintenance récupérée avec succès")
    @GetMapping
    public List<MaintenanceDTO> findAll() {
        return maintenanceService.findAll();
    }

    @Operation(summary = "Consulter une opération de maintenance par identifiant")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Maintenance trouvée"),
        @ApiResponse(responseCode = "404", description = "Maintenance introuvable")
    })
    @GetMapping("/{id}")
    public MaintenanceDTO findById(@Parameter(description = "Identifiant de la maintenance") @PathVariable Long id) {
        return maintenanceService.findById(id);
    }

    @Operation(summary = "Créer une opération de maintenance")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Maintenance créée avec succès"),
        @ApiResponse(responseCode = "400", description = "Données invalides ou dates incohérentes"),
        @ApiResponse(responseCode = "404", description = "Véhicule introuvable")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MaintenanceDTO create(@Parameter(description = "Données de la maintenance à créer") @Valid @RequestBody MaintenanceDTO maintenanceDTO) {
        return maintenanceService.create(maintenanceDTO);
    }

    @Operation(summary = "Modifier une opération de maintenance")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Maintenance modifiée avec succès"),
        @ApiResponse(responseCode = "400", description = "Données invalides"),
        @ApiResponse(responseCode = "404", description = "Maintenance ou véhicule introuvable")
    })
    @PutMapping("/{id}")
    public MaintenanceDTO update(@Parameter(description = "Identifiant de la maintenance") @PathVariable Long id,
                                 @Parameter(description = "Nouvelles données de la maintenance") @Valid @RequestBody MaintenanceDTO maintenanceDTO) {
        return maintenanceService.update(id, maintenanceDTO);
    }

    @Operation(summary = "Supprimer une opération de maintenance")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Maintenance supprimée avec succès"),
        @ApiResponse(responseCode = "404", description = "Maintenance introuvable")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Parameter(description = "Identifiant de la maintenance") @PathVariable Long id) {
        maintenanceService.deleteById(id);
    }
}
