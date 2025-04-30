package com.restaurant.reservation.service;

import java.util.List;
import com.restaurant.reservation.model.Reservation;
import com.restaurant.reservation.model.Restaurant;
import com.restaurant.reservation.repository.ReservationRepository;
import com.restaurant.reservation.repository.RestaurantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.nio.charset.StandardCharsets;

@Service
public class AdminService {

    private final ReservationRepository reservationRepository;
    private final EmailSenderService emailSenderService;
    private final RestaurantRepository restaurantRepository;

    public AdminService(ReservationRepository reservationRepository, EmailSenderService emailSenderService, RestaurantRepository restaurantRepository) {
        this.reservationRepository = reservationRepository;
        this.emailSenderService = emailSenderService;
        this.restaurantRepository = restaurantRepository;
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
    
    @Transactional
    public void updateHours(List<String> availableHours) {
        // Retrieve the single restaurant entity
        Restaurant restaurant = restaurantRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));
    
        // Update the availability hours
        restaurant.setAvailabilityHours(availableHours);
    
        // Save the updated restaurant entity
        restaurantRepository.save(restaurant);
    }


    @Transactional
    public String findLunchHours() {
        // Retrieve the single restaurant entity
        Restaurant restaurant = restaurantRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        // Return the availability hours
        return restaurant.getAvailabilityHours().get(0);
    }

    @Transactional
    public String findDinnerHours() {
        // Retrieve the single restaurant entity
        Restaurant restaurant = restaurantRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        // Return the availability hours
        return restaurant.getAvailabilityHours().get(1);
    }
}