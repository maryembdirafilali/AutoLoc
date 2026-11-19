package tn.esprit.autoloc.repository;

import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.autoloc.domain.Reservation;

@Repository
public interface IReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findFirstByDateDebutAndDateFin(LocalDate dateDebut, LocalDate dateFin);
}
