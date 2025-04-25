package com.restaurant.reservation;

import com.restaurant.reservation.model.Reservation;
import com.restaurant.reservation.repository.ReservationRepository;
import com.restaurant.reservation.service.AdminService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

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
    private List<Reservation> mockReservations;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        reservation1 = new Reservation();
        reservation2 = new Reservation();

        mockReservations = List.of(reservation1, reservation2);
    }

    @Test
    public void shouldReturnAllReservationsWithUser_whenFindAllReservationsCalled() {
        
        when(reservationRepository.findAllWithUser()).thenReturn(mockReservations);

        List<Reservation> result = adminService.findAllReservations();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(reservation1));
        assertTrue(result.contains(reservation2));
        verify(reservationRepository, times(1)).findAllWithUser();
    }
}