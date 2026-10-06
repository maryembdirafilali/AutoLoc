package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.domain.Reservation;

public interface IReservationService {
    Reservation saveReservation(Reservation reservation);
    Reservation updateReservation(Long id, Reservation reservation);
    Reservation getReservationById(Long id);
    List<Reservation> getAllReservations();
    void deleteReservation(Long id);
}
