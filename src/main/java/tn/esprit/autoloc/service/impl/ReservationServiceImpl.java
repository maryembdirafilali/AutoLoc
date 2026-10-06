package tn.esprit.autoloc.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.repository.IReservationRepository;
import tn.esprit.autoloc.service.IReservationService;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final IReservationRepository reservationRepository;

    @Override
    public Reservation saveReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation updateReservation(Long id, Reservation reservation) {
        return reservationRepository.findById(id).map(existingReservation -> {
            existingReservation.setDateDebut(reservation.getDateDebut());
            existingReservation.setDateFin(reservation.getDateFin());
            existingReservation.setStatut(reservation.getStatut());
            existingReservation.setVehicule(reservation.getVehicule());
            existingReservation.setClient(reservation.getClient());
            return reservationRepository.save(existingReservation);
        }).orElseThrow(() -> new RuntimeException("Reservation introuvable avec l'id : " + id));
    }

    @Override
    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation introuvable avec l'id : " + id));
    }

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
