package tn.esprit.autoloc.repository;

import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.autoloc.domain.Contrat;

@Repository
public interface IContratRepository extends JpaRepository<Contrat, Long> {

    Optional<Contrat> findFirstByDateSignature(LocalDate dateSignature);
}
