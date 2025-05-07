package com.restaurant.reservation.Performance;

import com.restaurant.reservation.model.Reservation;
import com.restaurant.reservation.model.Restaurant;
import com.restaurant.reservation.model.RestaurantTable;
import com.restaurant.reservation.model.User;
import com.restaurant.reservation.model.UserType;
import com.restaurant.reservation.repository.ReservationRepository;
import com.restaurant.reservation.repository.UserRepository;
import com.restaurant.reservation.repository.RestaurantTableRepository;
import com.restaurant.reservation.service.AdminService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest // To load the Spring context and use actual beans
public class AdminServicePerformanceTest {

    @Autowired
    private ReservationRepository reservationRepository;  // Use actual repository

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantTableRepository tableRepository;

    @Autowired
    private AdminService adminService;                    // Use actual service

    private Reservation reservation;
    private User user;
    private Restaurant restaurant;
    private RestaurantTable table;
    
    @BeforeEach
    public void setup() {
        reservationRepository.deleteAll();
        userRepository.deleteAll();
        tableRepository.deleteAll();

        // Create user
        user = new User();
        user.setUsername("Perf Test");
        user.setEmail("user@email.com");
        user.setPassword("pass");
        user.setPhone("600123456");
        user.setUserType(UserType.CUSTOMER);
        userRepository.save(user);

        // Create restaurant
        restaurant = new Restaurant();
        restaurant.setId(1L);  // Ensure the restaurant ID is set
        restaurant.setName("Performance Test Restaurant");

        // Create table
        table = new RestaurantTable();
        table.setCapacity(4);
        table.setState("available");
        table.setRestaurant(restaurant);  // Correctly associate the table with the restaurant
        tableRepository.save(table);

        // Create and save a reservation
        reservation = new Reservation();
        reservation.setUser(user);
        reservation.setDate(LocalDate.now().plusDays(1));
        reservation.setHour(LocalTime.of(20, 0));
        reservation.setnPeople(4);
        reservation.setState("PENDING");
        reservation.setTable(table);

        reservationRepository.save(reservation);
        System.out.println("Reservation successfully created for user: admin@example.com");
    }

    @Test
    public void performance_findAllReservations_under100ms() {
        // Testing the actual query to the database
        long start = System.currentTimeMillis();
        adminService.findAllReservations();
        long duration = System.currentTimeMillis() - start;

        System.out.println("findAllReservations() took: " + duration + " ms");
        assertTrue(duration < 100);  // Ensure it is under 100 ms
    }

    @Test
    public void performance_cancelReservation_under150ms() {
        // Using real repository and service
        Optional<Reservation> reservationOpt = reservationRepository.findById(1L);  // Using real DB access
        if (reservationOpt.isPresent()) {
            // Cancel the reservation
            long start = System.currentTimeMillis();
            adminService.cancelReservation(reservationOpt.get().getId());
            long duration = System.currentTimeMillis() - start;

            System.out.println("cancelReservation() took: " + duration + " ms");
            assertTrue(duration < 150);  // Ensure it is under 150 ms
        }
    }

    @Test
    public void performance_confirmReservation_under150ms() {
        // Using real repository and service
        Optional<Reservation> reservationOpt = reservationRepository.findById(1L);  // Using real DB access
        if (reservationOpt.isPresent()) {
            // Confirm the reservation
            long start = System.currentTimeMillis();
            adminService.confirmReservation(reservationOpt.get().getId());
            long duration = System.currentTimeMillis() - start;

            System.out.println("confirmReservation() took: " + duration + " ms");
            assertTrue(duration < 150);  // Ensure it is under 150 ms
        }
    }
}