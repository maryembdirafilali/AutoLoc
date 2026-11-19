package tn.esprit.autoloc.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.autoloc.domain.Maintenance;

@Repository
public interface IMaintenanceRepository extends JpaRepository<Maintenance, Long> {

    Optional<Maintenance> findFirstByDescription(String description);
}
