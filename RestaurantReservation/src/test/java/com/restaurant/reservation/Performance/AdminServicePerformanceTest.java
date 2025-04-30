package com.restaurant.reservation.Performance;

import com.restaurant.reservation.model.Reservation;
import com.restaurant.reservation.model.User;
import com.restaurant.reservation.repository.ReservationRepository;
import com.restaurant.reservation.service.AdminService;
import com.restaurant.reservation.service.EmailSenderService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class AdminServicePerformanceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private EmailSenderService emailSenderService;

    @InjectMocks
    private AdminService adminService;

    private Reservation reservation;
    private User user;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setEmail("admin@example.com");

        reservation = new Reservation();
        reservation.setUser(user);
    }

    @Test
    public void performance_findAllReservations_under100ms() {
        when(reservationRepository.findAllWithUser()).thenReturn(List.of(reservation, reservation));

        long start = System.currentTimeMillis();
        adminService.findAllReservations();
        long duration = System.currentTimeMillis() - start;

        System.out.println("findAllReservations() took: " + duration + " ms");
        assertTrue(duration < 100);
    }

    @Test
    public void performance_cancelReservation_under150ms() {
        when(reservationRepository.findById(anyLong())).thenReturn(Optional.of(reservation));
        doNothing().when(emailSenderService).sendEmail(any(), any(), any());

        long start = System.currentTimeMillis();
        adminService.cancelReservation(1L);
        long duration = System.currentTimeMillis() - start;

        System.out.println("cancelReservation() took: " + duration + " ms");
        assertTrue(duration < 150);
    }

    @Test
    public void performance_confirmReservation_under150ms() {
        when(reservationRepository.findById(anyLong())).thenReturn(Optional.of(reservation));
        doNothing().when(emailSenderService).sendEmail(any(), any(), any());

        long start = System.currentTimeMillis();
        adminService.confirmReservation(1L);
        long duration = System.currentTimeMillis() - start;

        System.out.println("confirmReservation() took: " + duration + " ms");
        assertTrue(duration < 150);
    }
}
