package com.restaurant.reservation;

import com.restaurant.reservation.dto.ReservationRequestDTO;
import com.restaurant.reservation.model.Reservation;
import com.restaurant.reservation.model.RestaurantTable;
import com.restaurant.reservation.model.User;
import com.restaurant.reservation.repository.ReservationRepository;
import com.restaurant.reservation.repository.RestaurantTableRepository;
import com.restaurant.reservation.repository.UserRepository;
import com.restaurant.reservation.service.CustomerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class CustomerServicePerformanceTest {

    @Mock private ReservationRepository reservationRepository;
    @Mock private RestaurantTableRepository tableRepository;
    @Mock private UserRepository userRepository;

    @InjectMocks private CustomerService customerService;

    private ReservationRequestDTO dto;
    private User user;
    private RestaurantTable table;


    

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        dto = new ReservationRequestDTO();
        dto.setDate(LocalDate.now().plusDays(1));
        dto.setHour(LocalTime.of(13, 30));
        dto.setnPeople(2);

        user = new User();
        user.setEmail("user@email.com");

        table = new RestaurantTable();
        table.setCapacity(4);
        table.setState("available");

        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken("user@email.com", null)
        );
    }

    @Test
    public void performance_makeReservation_under100ms() {
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        when(tableRepository.findAvailableTables(any(), any(), anyInt())).thenReturn(List.of(table));
        when(reservationRepository.countByDateAndHour(any(), any())).thenReturn(0L);

        long start = System.currentTimeMillis();
        customerService.makeReservation(dto);
        long time = System.currentTimeMillis() - start;

        System.out.println("makeReservation() took: " + time + " ms");
        assertTrue(time < 100);
    }

    @Test
    public void performance_updateReservation_under100ms() {
        Reservation reservation = new Reservation();
        reservation.setUser(user);

        when(reservationRepository.findById(anyLong())).thenReturn(Optional.of(reservation));
        when(tableRepository.findAvailableTables(any(), any(), anyInt())).thenReturn(List.of(table));
        when(reservationRepository.countByDateAndHour(any(), any())).thenReturn(0L);

        long start = System.currentTimeMillis();
        customerService.updateReservation(1L, dto);
        long time = System.currentTimeMillis() - start;

        System.out.println("updateReservation() took: " + time + " ms");
        assertTrue(time < 100);
    }

    @Test
    public void performance_deleteReservation_under50ms() {
        Reservation reservation = new Reservation();
        reservation.setUser(user);

        when(reservationRepository.findById(anyLong())).thenReturn(Optional.of(reservation));

        long start = System.currentTimeMillis();
        customerService.deleteReservation(1L);
        long time = System.currentTimeMillis() - start;

        System.out.println("deleteReservation() took: " + time + " ms");
        assertTrue(time < 50);
    }
}