package tn.esprit.autoloc.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.StatutReservation;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IClientRepository;
import tn.esprit.autoloc.repository.IContratRepository;
import tn.esprit.autoloc.repository.IReservationRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.service.IReservationService;
import tn.esprit.autoloc.web.dto.ReservationDTO;
import tn.esprit.autoloc.web.dto.ReservationResponseDTO;
import tn.esprit.autoloc.web.dto.ReservationWithContratRequestDTO;
import tn.esprit.autoloc.web.mapper.PaiementMapper;
import tn.esprit.autoloc.web.mapper.ReservationMapper;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final IReservationRepository reservationRepository;
    private final IClientRepository clientRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final IContratRepository contratRepository;
    private final ReservationMapper reservationMapper;
    private final PaiementMapper paiementMapper;

    @Override
    public ReservationDTO create(ReservationDTO reservationDTO) {
        if (reservationDTO.getIdReservation() != null) {
            throw new IllegalArgumentException("Une nouvelle réservation ne doit pas avoir d'identifiant");
        }
        if (reservationDTO.getDateDebut() != null && reservationDTO.getDateFin() != null &&
            reservationDTO.getDateFin().isBefore(reservationDTO.getDateDebut())) {
            throw new IllegalArgumentException("La date de fin de réservation ne peut pas être antérieure à la date de début");
        }

        Client client = clientRepository.findById(reservationDTO.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client", reservationDTO.getClientId()));
        Vehicule vehicule = vehiculeRepository.findById(reservationDTO.getVehiculeId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", reservationDTO.getVehiculeId()));

        Reservation reservation = reservationMapper.toEntity(reservationDTO);
        reservation.setClient(client);
        reservation.setVehicule(vehicule);

        Reservation saved = reservationRepository.save(reservation);
        return reservationMapper.toDto(saved);
    }

    @Override
    public ReservationDTO findById(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", id));
        return reservationMapper.toDto(reservation);
    }

    @Override
    public List<ReservationDTO> findAll() {
        return reservationRepository.findAll().stream()
                .map(reservationMapper::toDto)
                .toList();
    }

    @Override
    public ReservationDTO update(Long id, ReservationDTO reservationDTO) {
        Reservation existant = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", id));

        if (reservationDTO.getDateDebut() != null && reservationDTO.getDateFin() != null &&
            reservationDTO.getDateFin().isBefore(reservationDTO.getDateDebut())) {
            throw new IllegalArgumentException("La date de fin de réservation ne peut pas être antérieure à la date de début");
        }

        if (reservationDTO.getClientId() != null) {
            Client client = clientRepository.findById(reservationDTO.getClientId())
                    .orElseThrow(() -> new ResourceNotFoundException("Client", reservationDTO.getClientId()));
            existant.setClient(client);
        }

        if (reservationDTO.getVehiculeId() != null) {
            Vehicule vehicule = vehiculeRepository.findById(reservationDTO.getVehiculeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Vehicule", reservationDTO.getVehiculeId()));
            existant.setVehicule(vehicule);
        }

        existant.setDateDebut(reservationDTO.getDateDebut());
        existant.setDateFin(reservationDTO.getDateFin());
        existant.setStatut(reservationDTO.getStatut());

        Reservation updated = reservationRepository.save(existant);
        return reservationMapper.toDto(updated);
    }

    @Override
    public void deleteById(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reservation", id);
        }
        reservationRepository.deleteById(id);
    }

    @Override
    public ReservationResponseDTO affecterReservationAClient(Long idReservation, String nom, String prenom) {
        Reservation reservation = reservationRepository.findById(idReservation)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", idReservation));

        Client client = clientRepository.findByNomAndPrenom(nom, prenom)
                .orElseThrow(() -> new ResourceNotFoundException("Client de nom '" + nom + "' et prénom '" + prenom + "'"));

        reservation.setClient(client);
        Reservation saved = reservationRepository.save(reservation);
        return reservationMapper.toResponseDto(saved);
    }

    @Override
    public ReservationResponseDTO creerReservationEtContratAssocie(ReservationWithContratRequestDTO requestDTO) {
        if (requestDTO.getDateDebut() != null && requestDTO.getDateFin() != null &&
            requestDTO.getDateFin().isBefore(requestDTO.getDateDebut())) {
            throw new IllegalArgumentException("La date de fin de réservation ne peut pas être antérieure à la date de début");
        }

        Client client = clientRepository.findById(requestDTO.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client", requestDTO.getClientId()));

        Vehicule vehicule = vehiculeRepository.findById(requestDTO.getVehiculeId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", requestDTO.getVehiculeId()));

        Reservation reservation = Reservation.builder()
                .dateDebut(requestDTO.getDateDebut())
                .dateFin(requestDTO.getDateFin())
                .statut(requestDTO.getStatut() != null ? requestDTO.getStatut() : StatutReservation.EN_ATTENTE)
                .client(client)
                .vehicule(vehicule)
                .build();

        Reservation savedReservation = reservationRepository.save(reservation);

        BigDecimal montant = requestDTO.getMontantTotal();
        if (montant == null && vehicule.getTarifJournalier() != null) {
            long jours = ChronoUnit.DAYS.between(requestDTO.getDateDebut(), requestDTO.getDateFin());
            if (jours <= 0) {
                jours = 1;
            }
            montant = vehicule.getTarifJournalier().multiply(BigDecimal.valueOf(jours));
        }

        Contrat contrat = Contrat.builder()
                .dateSignature(requestDTO.getDateSignature() != null ? requestDTO.getDateSignature() : LocalDate.now())
                .montantTotal(montant != null ? montant : BigDecimal.ZERO)
                .valide(requestDTO.isValide())
                .reservation(savedReservation)
                .build();

        if (requestDTO.getPaiements() != null && !requestDTO.getPaiements().isEmpty()) {
            List<Paiement> paiements = requestDTO.getPaiements().stream()
                    .map(pDto -> {
                        Paiement p = paiementMapper.toEntity(pDto);
                        p.setContrat(contrat);
                        return p;
                    })
                    .toList();
            contrat.setPaiements(paiements);
        }

        Contrat savedContrat = contratRepository.save(contrat);
        savedReservation.setContrat(savedContrat);

        return reservationMapper.toResponseDto(savedReservation);
    }
}
