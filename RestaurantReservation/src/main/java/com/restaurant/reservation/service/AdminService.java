package com.restaurant.reservation.service;

import java.util.List;
import com.restaurant.reservation.model.Reservation;
import com.restaurant.reservation.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {

    private final ReservationRepository reservationRepository;
    private final EmailSenderService emailSenderService;

    public AdminService(ReservationRepository reservationRepository, EmailSenderService emailSenderService) {
        this.reservationRepository = reservationRepository;
        this.emailSenderService = emailSenderService;
    }

    public List<Reservation> findAllReservations() {
        return reservationRepository.findAllWithUser();
    }

    @Transactional
    public void cancelReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Reservation not found"));

        reservation.setState("cancelled");
        reservationRepository.save(reservation);

        // ENVÍO DE EMAIL
        String userEmail = reservation.getUser().getEmail();
        emailSenderService.sendEmail(
            userEmail,
            "Reservation Cancelled",
            "Dear customer,\n\nYour reservation has been cancelled.\n\nThank you!"
        );
    }

    @Transactional
    public void confirmReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Reservation not found"));

        reservation.setState("confirmed");
        reservationRepository.save(reservation);

        // ENVÍO DE EMAIL
        String userEmail = reservation.getUser().getEmail();
        emailSenderService.sendEmail(
            userEmail,
            "Reservation Confirmed",
            "Dear customer,\n\nYour reservation has been confirmed successfully.\n\nThank you!"
        );
    }
}