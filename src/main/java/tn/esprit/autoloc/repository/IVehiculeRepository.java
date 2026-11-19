package tn.esprit.autoloc.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.autoloc.domain.Vehicule;

@Repository
public interface IVehiculeRepository extends JpaRepository<Vehicule, Long> {

    Optional<Vehicule> findByImmatriculation(String immatriculation);
}
