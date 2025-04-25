package com.restaurant.reservation;

import com.restaurant.reservation.model.Reservation;
import com.restaurant.reservation.repository.ReservationRepository;
import com.restaurant.reservation.service.AdminService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private AdminService adminService;

    private Reservation reservation1;
    private Reservation reservation2;

    @BeforeEach
    public void setup() {
        reservation1 = new Reservation();
        reservation2 = new Reservation();
    }

    @Test
    public void shouldReturnAllReservationsWithUser_whenFindAllReservationsCalled() {
        List<Reservation> mockReservations = List.of(reservation1, reservation2);
        when(reservationRepository.findAllWithUser()).thenReturn(mockReservations);

        List<Reservation> result = adminService.findAllReservations();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(reservation1));
        assertTrue(result.contains(reservation2));
        verify(reservationRepository, times(1)).findAllWithUser();
    }

    @Test
    public void shouldCancelReservation_whenValidIdProvided() {
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation1));

        adminService.cancelReservation(1L);

        assertEquals("cancelled", reservation1.getState());
        verify(reservationRepository).save(reservation1);
    }

    @Test
    public void shouldThrowException_whenCancellingNonExistingReservation() {
        when(reservationRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> adminService.cancelReservation(999L));
        verify(reservationRepository, never()).save(any());
    }

    @Test
    public void shouldConfirmReservation_whenValidIdProvided() {
        when(reservationRepository.findById(2L)).thenReturn(Optional.of(reservation2));

        adminService.confirmReservation(2L);

        assertEquals("confirmed", reservation2.getState());
        verify(reservationRepository).save(reservation2);
    }

    @Test
    public void shouldThrowException_whenConfirmingNonExistingReservation() {
        when(reservationRepository.findById(123L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> adminService.confirmReservation(123L));
        verify(reservationRepository, never()).save(any());
    }
}