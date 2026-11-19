package tn.esprit.autoloc.web.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.web.dto.ReservationDTO;
import tn.esprit.autoloc.web.dto.ReservationResponseDTO;

@Component
@RequiredArgsConstructor
public class ReservationMapper {

    private final ContratMapper contratMapper;

    public ReservationDTO toDto(Reservation entity) {
        if (entity == null) {
            return null;
        }
        return ReservationDTO.builder()
                .idReservation(entity.getIdReservation())
                .dateDebut(entity.getDateDebut())
                .dateFin(entity.getDateFin())
                .statut(entity.getStatut())
                .clientId(entity.getClient() != null ? entity.getClient().getIdClient() : null)
                .vehiculeId(entity.getVehicule() != null ? entity.getVehicule().getIdVehicule() : null)
                .contratId(entity.getContrat() != null ? entity.getContrat().getIdContrat() : null)
                .build();
    }

    public ReservationResponseDTO toResponseDto(Reservation entity) {
        if (entity == null) {
            return null;
        }
        return ReservationResponseDTO.builder()
                .idReservation(entity.getIdReservation())
                .dateDebut(entity.getDateDebut())
                .dateFin(entity.getDateFin())
                .statut(entity.getStatut())
                .clientId(entity.getClient() != null ? entity.getClient().getIdClient() : null)
                .clientNom(entity.getClient() != null ? entity.getClient().getNom() : null)
                .clientPrenom(entity.getClient() != null ? entity.getClient().getPrenom() : null)
                .clientEmail(entity.getClient() != null ? entity.getClient().getEmail() : null)
                .vehiculeId(entity.getVehicule() != null ? entity.getVehicule().getIdVehicule() : null)
                .vehiculeImmatriculation(entity.getVehicule() != null ? entity.getVehicule().getImmatriculation() : null)
                .vehiculeMarque(entity.getVehicule() != null ? entity.getVehicule().getMarque() : null)
                .vehiculeModele(entity.getVehicule() != null ? entity.getVehicule().getModele() : null)
                .contratId(entity.getContrat() != null ? entity.getContrat().getIdContrat() : null)
                .contrat(entity.getContrat() != null ? contratMapper.toDto(entity.getContrat()) : null)
                .build();
    }

    public Reservation toEntity(ReservationDTO dto) {
        if (dto == null) {
            return null;
        }
        Client client = null;
        if (dto.getClientId() != null) {
            client = Client.builder().idClient(dto.getClientId()).build();
        }

        Vehicule vehicule = null;
        if (dto.getVehiculeId() != null) {
            vehicule = Vehicule.builder().idVehicule(dto.getVehiculeId()).build();
        }

        Contrat contrat = null;
        if (dto.getContratId() != null) {
            contrat = Contrat.builder().idContrat(dto.getContratId()).build();
        }

        return Reservation.builder()
                .idReservation(dto.getIdReservation())
                .dateDebut(dto.getDateDebut())
                .dateFin(dto.getDateFin())
                .statut(dto.getStatut())
                .client(client)
                .vehicule(vehicule)
                .contrat(contrat)
                .build();
    }
}
