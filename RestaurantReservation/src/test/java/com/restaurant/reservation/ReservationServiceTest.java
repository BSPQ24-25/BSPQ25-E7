package com.restaurant.reservation;

import com.restaurant.reservation.dto.ReservationDTO;
import com.restaurant.reservation.model.Customer;
import com.restaurant.reservation.model.Reservation;
import com.restaurant.reservation.model.RestaurantTable;
import com.restaurant.reservation.repository.CustomerRepository;
import com.restaurant.reservation.repository.ReservationRepository;
import com.restaurant.reservation.repository.TableRepository;
import com.restaurant.reservation.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReservationServiceTest {

    private ReservationRepository reservationRepository;
    private CustomerRepository customerRepository;
    private TableRepository tableRepository;
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        reservationRepository = mock(ReservationRepository.class);
        customerRepository = mock(CustomerRepository.class);
        tableRepository = mock(TableRepository.class);
        reservationService = new ReservationService(reservationRepository, customerRepository, tableRepository);
    }

    @Test
    void testCreateReservation() {
        ReservationDTO dto = new ReservationDTO();
        dto.setCustomerId(1L);
        dto.setTableId(1L);
        // Usamos LocalDate.parse() y LocalTime.parse() para convertir las cadenas a objetos correspondientes
        dto.setDate(LocalDate.parse("2025-04-01"));  // Convertir la fecha a LocalDate
        dto.setHour(LocalTime.parse("19:00"));      // Convertir la hora a LocalTime
        dto.setnPeople(4);

        // Mockear Customer y Table
        Customer customer = new Customer();
        customer.setId(1L);
        RestaurantTable table = new RestaurantTable();
        table.setTableId(1L);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(tableRepository.findById(1L)).thenReturn(Optional.of(table));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(new Reservation());

        // Ejecutar el método
        Reservation createdReservation = reservationService.createReservation(dto);

        // Verificar el resultado
        assertNotNull(createdReservation);
        verify(reservationRepository).save(any(Reservation.class));
    }


    @Test
    void testGetReservationsByCustomer() {
        List<Reservation> reservations = Arrays.asList(new Reservation(), new Reservation());
        when(reservationRepository.findByCustomerId(1L)).thenReturn(reservations);

        List<Reservation> result = reservationService.getReservationsByCustomer(1L);

        assertEquals(2, result.size());
    }
}
