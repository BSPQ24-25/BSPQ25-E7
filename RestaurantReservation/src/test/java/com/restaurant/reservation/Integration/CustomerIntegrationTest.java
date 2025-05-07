package com.restaurant.reservation.Integration;

import com.restaurant.reservation.dto.ReservationRequestDTO;
import com.restaurant.reservation.model.Reservation;
import com.restaurant.reservation.model.User;
import com.restaurant.reservation.model.UserType;
import com.restaurant.reservation.repository.ReservationRepository;
import com.restaurant.reservation.repository.UserRepository;
import com.restaurant.reservation.service.CustomerService;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Optional: not strictly necessary anymore
public class CustomerIntegrationTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    private ReservationRequestDTO dto;
    private final String testEmail = "cliente.test@example.com";

    @BeforeEach
    public void setUp() {
        // Clean up database before each test
        reservationRepository.deleteAll();
        userRepository.deleteAll();

        // Create a test user
        User user = new User();
        user.setUsername("Test Customer");
        user.setEmail(testEmail);
        user.setPassword("test1234");
        user.setPhone("600123456");
        user.setUserType(UserType.CUSTOMER);
        userRepository.save(user);

        // Prepare a valid reservation request
        dto = new ReservationRequestDTO();
        dto.setDate(LocalDate.now().plusDays(1));
        dto.setHour(LocalTime.of(13, 0));
        dto.setnPeople(2);
    }

    // Helper method to create a reservation using the DTO
    private void createReservation() {
        customerService.makeReservation(dto);
    }

    @Test
    @Order(1)
    @WithMockUser(username = "cliente.test@example.com", roles = "CUSTOMER")
    public void testCreateReservation() {
        createReservation();
        List<Reservation> reservations = reservationRepository.findAll();
        assertFalse(reservations.isEmpty(), "The reservation should be created.");
    }

    @Test
    @Order(2)
    @WithMockUser(username = "cliente.test@example.com", roles = "CUSTOMER")
    public void testFindReservationByUser() {
        createReservation();
        User user = userRepository.findByEmail(testEmail).orElseThrow();
        List<Reservation> reservations = reservationRepository.findByUser(user);
        assertFalse(reservations.isEmpty(), "The user should have at least one reservation.");
    }

    @Test
    @Order(3)
    @WithMockUser(username = "cliente.test@example.com", roles = "CUSTOMER")
    public void testDeleteReservation() {
        createReservation();
        User user = userRepository.findByEmail(testEmail).orElseThrow();
        List<Reservation> reservations = reservationRepository.findByUser(user);
        assertFalse(reservations.isEmpty(), "The reservation should exist before deletion.");

        Long reservationId = reservations.get(0).getId();
        reservationRepository.deleteById(reservationId);

        List<Reservation> afterDeletion = reservationRepository.findByUser(user);
        boolean stillExists = afterDeletion.stream().anyMatch(r -> r.getId().equals(reservationId));
        assertFalse(stillExists, "The reservation should be deleted.");
    }
}