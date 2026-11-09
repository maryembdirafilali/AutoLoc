package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.web.dto.ReservationDTO;

public interface IReservationService {
    ReservationDTO create(ReservationDTO reservationDTO);
    ReservationDTO findById(Long id);
    List<ReservationDTO> findAll();
    ReservationDTO update(Long id, ReservationDTO reservationDTO);
    void deleteById(Long id);
}
