package com.restaurant.reservation;

import com.restaurant.reservation.model.Customer;
import com.restaurant.reservation.model.Reservation;
import com.restaurant.reservation.model.RestaurantTable;
import com.restaurant.reservation.model.Reservation.ReservationState;
import com.restaurant.reservation.repository.ReservationRepository;
import com.restaurant.reservation.service.ReservationService;
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
        Reservation reservation = createSampleReservation();
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        Reservation saved = reservationService.createReservation(reservation);
        assertNotNull(saved);
        assertEquals(ReservationState.CONFIRMED, saved.getState());
    }

    @Test
    void testGetAllReservations() {
        when(reservationRepository.findAll()).thenReturn(Arrays.asList(
                createSampleReservation(),
                createSampleReservation()
        ));

        List<Reservation> result = reservationService.getAllReservations();
        assertEquals(2, result.size());
    }

    @Test
    void testGetReservationById() {
        Reservation reservation = createSampleReservation();
        reservation.setReservationId(1L);

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        Optional<Reservation> found = reservationService.getReservationById(1L);
        assertTrue(found.isPresent());
        assertEquals(1L, found.get().getReservationId());
    }

    @Test
    void testCancelReservation() {
        doNothing().when(reservationRepository).deleteById(1L);

        reservationService.cancelReservation(1L);

        verify(reservationRepository, times(1)).deleteById(1L);
    }

    // Método auxiliar para crear una reserva ficticia
    private Reservation createSampleReservation() {
        Reservation r = new Reservation();
        r.setCustomer(mock(Customer.class));
        r.setTable(mock(RestaurantTable.class)); // actualizado
        r.setDateTime(LocalDateTime.now().plusDays(1));
        r.setnPeople(2);
        r.setState(ReservationState.CONFIRMED);
        return r;
    }
}
