package com.restaurant.reservation;

import com.restaurant.reservation.model.Reservation;
import com.restaurant.reservation.model.User;
import com.restaurant.reservation.repository.ReservationRepository;
import com.restaurant.reservation.service.AdminService;
import com.restaurant.reservation.service.EmailSenderService; // <- Faltaba importar

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AdminServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private EmailSenderService emailSenderService;

    @InjectMocks
    private AdminService adminService;

    private Reservation reservation1;
    private Reservation reservation2;

    @BeforeEach
    public void setup() {
        User user1 = new User();
        user1.setEmail("user1@email.com");

        User user2 = new User();
        user2.setEmail("user2@email.com");

        reservation1 = new Reservation();
        reservation1.setUser(user1);

        reservation2 = new Reservation();
        reservation2.setUser(user2);
    }

    @Test
    public void shouldReturnAllReservationsWithUser_whenFindAllReservationsCalled() {
        when(reservationRepository.findAllWithUser()).thenReturn(List.of(reservation1, reservation2));

        List<Reservation> result = adminService.findAllReservations();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(reservation1));
        assertTrue(result.contains(reservation2));
        verify(reservationRepository, times(1)).findAllWithUser();
    }

    @Test
    public void shouldCancelReservation_whenValidIdProvided() {
        when(reservationRepository.findById(anyLong())).thenReturn(Optional.of(reservation1));

        adminService.cancelReservation(1L);

        assertEquals("cancelled", reservation1.getState());
        verify(reservationRepository).save(reservation1);
        verify(emailSenderService).sendEmail(anyString(), anyString(), anyString());
    }

    @Test
    public void shouldThrowException_whenCancellingNonExistingReservation() {
        when(reservationRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> adminService.cancelReservation(999L));
        verify(reservationRepository, never()).save(any());
        verify(emailSenderService, never()).sendEmail(anyString(), anyString(), anyString());
    }

    @Test
    public void shouldConfirmReservation_whenValidIdProvided() {
        when(reservationRepository.findById(anyLong())).thenReturn(Optional.of(reservation2));

        adminService.confirmReservation(2L);

        assertEquals("confirmed", reservation2.getState());
        verify(reservationRepository).save(reservation2);
        verify(emailSenderService).sendEmail(anyString(), anyString(), anyString());
    }

    @Test
    public void shouldThrowException_whenConfirmingNonExistingReservation() {
        when(reservationRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> adminService.confirmReservation(123L));
        verify(reservationRepository, never()).save(any());
        verify(emailSenderService, never()).sendEmail(anyString(), anyString(), anyString());
    }
}