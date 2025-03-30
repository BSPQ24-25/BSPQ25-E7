package com.restaurant.reservation;

import com.restaurant.reservation.model.Customer;
import com.restaurant.reservation.model.Reservation;
import com.restaurant.reservation.model.Table;
import com.restaurant.reservation.repository.ReservationRepository;
import com.restaurant.reservation.service.ReservationService;
import com.restaurant.reservation.model.Reservation.ReservationState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReservationServiceTest {

    private ReservationRepository reservationRepository;
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        reservationRepository = mock(ReservationRepository.class);
        reservationService = new ReservationService(reservationRepository);
    }

    @Test
    void testCreateReservation() {
        Customer mockCustomer = mock(Customer.class);
        Table mockTable = mock(Table.class);

        Reservation reservation = new Reservation();
        reservation.setCustomer(mockCustomer);
        reservation.setTable(mockTable);
        reservation.setDateTime(LocalDateTime.now().plusDays(1));
        reservation.setnPeople(4);
        reservation.setState(ReservationState.CONFIRMED);

        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        Reservation saved = reservationService.createReservation(reservation);
        assertNotNull(saved);
        assertEquals(4, saved.getnPeople());
        assertEquals(ReservationState.CONFIRMED, saved.getState());
    }

    @Test
    void testGetReservationById() {
        Reservation reservation = new Reservation();
        reservation.setReservationId(1L);
        reservation.setState(ReservationState.PENDING);

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        Optional<Reservation> result = reservationService.getReservationById(1L);
        assertTrue(result.isPresent());
        assertEquals(ReservationState.PENDING, result.get().getState());
    }

    @Test
    void testGetAllReservations() {
        Reservation res1 = new Reservation();
        Reservation res2 = new Reservation();

        when(reservationRepository.findAll()).thenReturn(Arrays.asList(res1, res2));

        List<Reservation> result = reservationService.getAllReservations();
        assertEquals(2, result.size());
    }

    @Test
    void testCancelReservation() {
        doNothing().when(reservationRepository).deleteById(3L);
        reservationService.cancelReservation(3L);
        verify(reservationRepository, times(1)).deleteById(3L);
    }
}
