package tn.esprit.autoloc.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IContratRepository;
import tn.esprit.autoloc.repository.IReservationRepository;
import tn.esprit.autoloc.service.IContratService;
import tn.esprit.autoloc.web.dto.ContratDTO;
import tn.esprit.autoloc.web.mapper.ContratMapper;

@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements IContratService {

    private final IContratRepository contratRepository;
    private final IReservationRepository reservationRepository;
    private final ContratMapper contratMapper;

    @Override
    public ContratDTO create(ContratDTO contratDTO) {
        if (contratDTO.getIdContrat() != null) {
            throw new IllegalArgumentException("Un nouveau contrat ne doit pas avoir d'identifiant");
        }
        if (contratDTO.getMontantTotal() != null && contratDTO.getMontantTotal().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Le montant total d'un contrat ne peut pas être négatif");
        }

        Contrat contrat = contratMapper.toEntity(contratDTO);

        if (contratDTO.getReservationId() != null) {
            Reservation reservation = reservationRepository.findById(contratDTO.getReservationId())
                    .orElseThrow(() -> new ResourceNotFoundException("Reservation", contratDTO.getReservationId()));
            contrat.setReservation(reservation);
        }

        if (contrat.getPaiements() != null) {
            contrat.getPaiements().forEach(p -> p.setContrat(contrat));
        }

        Contrat saved = contratRepository.save(contrat);
        return contratMapper.toDto(saved);
    }

    @Override
    public ContratDTO findById(Long id) {
        Contrat contrat = contratRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contrat", id));
        return contratMapper.toDto(contrat);
    }

    @Override
    public List<ContratDTO> findAll() {
        return contratRepository.findAll().stream()
                .map(contratMapper::toDto)
                .toList();
    }

    @Override
    public ContratDTO update(Long id, ContratDTO contratDTO) {
        Contrat existant = contratRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contrat", id));

        if (contratDTO.getMontantTotal() != null && contratDTO.getMontantTotal().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Le montant total d'un contrat ne peut pas être négatif");
        }

        existant.setMontantTotal(contratDTO.getMontantTotal());
        existant.setDateSignature(contratDTO.getDateSignature());
        existant.setValide(contratDTO.isValide());

        if (contratDTO.getReservationId() != null) {
            Reservation reservation = reservationRepository.findById(contratDTO.getReservationId())
                    .orElseThrow(() -> new ResourceNotFoundException("Reservation", contratDTO.getReservationId()));
            existant.setReservation(reservation);
        }

        Contrat updated = contratRepository.save(existant);
        return contratMapper.toDto(updated);
    }

    @Override
    public void deleteById(Long id) {
        if (!contratRepository.existsById(id)) {
            throw new ResourceNotFoundException("Contrat", id);
        }
        contratRepository.deleteById(id);
    }

    @Override
    public ContratDTO affecterContratAReservation(LocalDate dateSignature, LocalDate dateDebut, LocalDate dateFin) {
        Reservation reservation = reservationRepository.findFirstByDateDebutAndDateFin(dateDebut, dateFin)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation pour la période du " + dateDebut + " au " + dateFin + " introuvable"));

        BigDecimal montantTotal = BigDecimal.ZERO;
        if (reservation.getVehicule() != null && reservation.getVehicule().getTarifJournalier() != null) {
            long jours = ChronoUnit.DAYS.between(dateDebut, dateFin);
            if (jours <= 0) {
                jours = 1;
            }
            montantTotal = reservation.getVehicule().getTarifJournalier().multiply(BigDecimal.valueOf(jours));
        }

        final BigDecimal finalMontant = montantTotal;
        Contrat contrat = contratRepository.findFirstByDateSignature(dateSignature)
                .orElseGet(() -> Contrat.builder()
                        .dateSignature(dateSignature)
                        .montantTotal(finalMontant)
                        .valide(true)
                        .build());

        contrat.setReservation(reservation);
        reservation.setContrat(contrat);

        Contrat saved = contratRepository.save(contrat);
        reservationRepository.save(reservation);

        return contratMapper.toDto(saved);
    }
}
