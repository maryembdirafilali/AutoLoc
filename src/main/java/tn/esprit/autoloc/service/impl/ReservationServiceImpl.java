package tn.esprit.autoloc.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IClientRepository;
import tn.esprit.autoloc.repository.IReservationRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.service.IReservationService;
import tn.esprit.autoloc.web.dto.ReservationDTO;
import tn.esprit.autoloc.web.mapper.ReservationMapper;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final IReservationRepository reservationRepository;
    private final IClientRepository clientRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final ReservationMapper reservationMapper;

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
}
