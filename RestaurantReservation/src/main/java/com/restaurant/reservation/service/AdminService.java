package com.restaurant.reservation.service;

import java.util.List;
import com.restaurant.reservation.model.Reservation;
import com.restaurant.reservation.repository.ReservationRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final ReservationRepository reservationRepository;

    public AdminService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> findAllReservations() {
        return reservationRepository.findAllWithUser();
    }

    public void cancelReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Reservation not found"));
        reservation.setState("cancelled");
        reservationRepository.save(reservation);
    }

    public void confirmReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
            .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
    
        reservation.setState("confirmed");
        reservationRepository.save(reservation);
    }    
}