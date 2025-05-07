package com.restaurant.reservation.service;

import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restaurant.reservation.model.Reservation;
import com.restaurant.reservation.repository.ReservationRepository;

@Service
public class AdminService {

    private final ReservationRepository reservationRepository;
    private final EmailSenderService emailSenderService;
    
    private String openingHour = "09:00";
    private String closingHour = "22:00";


    public AdminService(ReservationRepository reservationRepository, EmailSenderService emailSenderService) {
        this.reservationRepository = reservationRepository;
        this.emailSenderService = emailSenderService;
    }

    public String getOpeningHour() {
        return openingHour;
    }
    
    public void setOpeningHour(String openingHour) {
        this.openingHour = openingHour;
    }
    
    public String getClosingHour() {
        return closingHour;
    }
    
    public void setClosingHour(String closingHour) {
        this.closingHour = closingHour;
    }

    public LocalTime getOpeningHourAsTime() {
        return LocalTime.parse(openingHour);
    }

    public LocalTime getClosingHourAsTime() {
        return LocalTime.parse(closingHour);
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

        // ENVÍO DE EMAIL con plantilla HTML
        String userEmail = reservation.getUser().getEmail();
        String htmlContent = loadTemplate("templates/email/cancellation_email.html");
        emailSenderService.sendEmail(
            userEmail,
            "Reservation Cancelled",
            htmlContent
        );
    }

    @Transactional
    public void confirmReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Reservation not found"));

        reservation.setState("confirmed");
        reservationRepository.save(reservation);

        // ENVÍO DE EMAIL con plantilla HTML
        String userEmail = reservation.getUser().getEmail();
        String htmlContent = loadTemplate("templates/email/confirmation_email.html");
        emailSenderService.sendEmail(
            userEmail,
            "Reservation Confirmed",
            htmlContent
        );
    }

    private String loadTemplate(String path) {
        try {
            return new String(
                getClass().getClassLoader().getResourceAsStream(path).readAllBytes(),
                StandardCharsets.UTF_8
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to load email template: " + path, e);
        }
    }
}