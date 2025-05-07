package com.restaurant.reservation.Performance;

import com.restaurant.reservation.dto.ReservationRequestDTO;
import com.restaurant.reservation.model.Reservation;
import com.restaurant.reservation.model.Restaurant;
import com.restaurant.reservation.model.RestaurantTable;
import com.restaurant.reservation.model.User;
import com.restaurant.reservation.model.UserType;
import com.restaurant.reservation.repository.ReservationRepository;
import com.restaurant.reservation.repository.RestaurantTableRepository;
import com.restaurant.reservation.repository.UserRepository;
import com.restaurant.reservation.service.CustomerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CustomerServicePerformanceTest {

    @Autowired private CustomerService customerService;
    @Autowired private ReservationRepository reservationRepository;
    @Autowired private RestaurantTableRepository tableRepository;
    @Autowired private UserRepository userRepository;

    private ReservationRequestDTO dto;
    private User user;
    private RestaurantTable table;
    private Restaurant restaurant;

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

        // Auth context
        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(user.getEmail(), null)
        );

        // Create valid DTO
        dto = new ReservationRequestDTO();
        dto.setDate(LocalDate.now().plusDays(1));
        dto.setHour(LocalTime.of(13, 30));
        dto.setnPeople(2);
    }

    @Test
    public void performance_makeReservation_under100ms() {
        long start = System.currentTimeMillis();
        customerService.makeReservation(dto);
        long time = System.currentTimeMillis() - start;

        System.out.println("makeReservation() took: " + time + " ms");
        assertTrue(time < 100);
    }

    @Test
    public void performance_updateReservation_under100ms() {
        // Ensure the initial table is saved and available for updating
        // This table has enough capacity for both reservation creation and update
        RestaurantTable bigTable = new RestaurantTable();
        bigTable.setCapacity(6);
        bigTable.setState("available");
        bigTable.setRestaurant(restaurant); // Correctly associate the table with the restaurant
        tableRepository.save(bigTable);

        // Ensure the dto initially requests fewer people
        dto.setnPeople(2);
        customerService.makeReservation(dto);

        // Fetch the newly created reservation
        Reservation reservation = reservationRepository.findAll().get(0);

        // Modify the dto for the update
        dto.setnPeople(3); // Ensure the updated number of people is less than the table's capacity

        long start = System.currentTimeMillis();
        customerService.updateReservation(reservation.getId(), dto);
        long time = System.currentTimeMillis() - start;

        System.out.println("updateReservation() took: " + time + " ms");
        assertTrue(time < 100);
    }

    @Test
    public void performance_deleteReservation_under50ms() {
        customerService.makeReservation(dto);
        Reservation reservation = reservationRepository.findAll().get(0);

        long start = System.currentTimeMillis();
        customerService.deleteReservation(reservation.getId());
        long time = System.currentTimeMillis() - start;

        System.out.println("deleteReservation() took: " + time + " ms");
        assertTrue(time < 50);
    }
}