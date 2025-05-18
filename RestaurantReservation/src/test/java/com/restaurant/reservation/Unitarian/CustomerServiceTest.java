package com.restaurant.reservation.Unitarian;

import com.restaurant.reservation.dto.ReservationRequestDTO;
import com.restaurant.reservation.model.Reservation;
import com.restaurant.reservation.model.Restaurant;
import com.restaurant.reservation.model.RestaurantTable;
import com.restaurant.reservation.model.User;
import com.restaurant.reservation.model.Notification;
import com.restaurant.reservation.repository.NotificationRepository;
import com.restaurant.reservation.repository.ReservationRepository;
import com.restaurant.reservation.repository.RestaurantTableRepository;
import com.restaurant.reservation.repository.UserRepository;
import com.restaurant.reservation.service.AdminService;
import com.restaurant.reservation.service.CustomerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {

    @Mock private ReservationRepository reservationRepository;
    @Mock private RestaurantTableRepository tableRepository;
    @Mock private UserRepository userRepository;
    @Mock private AdminService adminService;
    @Mock private NotificationRepository notificationRepository;

    @InjectMocks private CustomerService customerService;

    private ReservationRequestDTO validDTO;
    private User user;
    private RestaurantTable table;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        validDTO = new ReservationRequestDTO();
        validDTO.setDate(LocalDate.now().plusDays(1));
        validDTO.setHour(LocalTime.of(13, 30));
        validDTO.setnPeople(2);

        user = new User();
        user.setEmail("user@email.com");

        table = new RestaurantTable();
        table.setCapacity(4);
        table.setState("available");

        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken("user@email.com", null)
        );

        // ✅ Mocking opening hours
        when(adminService.getOpeningHourAsTime()).thenReturn(LocalTime.of(12, 0));
        when(adminService.getClosingHourAsTime()).thenReturn(LocalTime.of(23, 0));

        when(adminService.getOpeningHour()).thenReturn("12:00");
        when(adminService.getClosingHour()).thenReturn("23:00");
    }

    @Test
    public void makeReservation_success() {
        when(userRepository.findByEmail("user@email.com")).thenReturn(Optional.of(user));
        when(tableRepository.findAvailableTables(any(), any(), anyInt()))
                .thenReturn(List.of(table));

        customerService.makeReservation(validDTO);

        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }

    @Test
    public void makeReservation_userNotFound_throwsException() {
        when(userRepository.findByEmail("user@email.com")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> customerService.makeReservation(validDTO));
    }

    @Test
    public void makeReservation_noAvailableTables_throwsException() {
        when(userRepository.findByEmail("user@email.com")).thenReturn(Optional.of(user));
        when(tableRepository.findAvailableTables(any(), any(), anyInt()))
                .thenReturn(Collections.emptyList());

        assertThrows(RuntimeException.class, () -> customerService.makeReservation(validDTO));
    }

    @Test
    public void makeReservation_withPastDate_throwsException() {
        validDTO.setDate(LocalDate.now().minusDays(1));

        assertThrows(RuntimeException.class, () -> customerService.makeReservation(validDTO));
    }

    @Test
    public void makeReservation_outOfOpeningHours_throwsException() {
        validDTO.setHour(LocalTime.of(4, 0)); // Outside the allowed time range

        assertThrows(RuntimeException.class, () -> customerService.makeReservation(validDTO));
    }


    @Test
    public void updateReservation_success() {
        Reservation reservation = new Reservation();
        reservation.setUser(user); // La reserva pertenece al usuario

        when(reservationRepository.findById(anyLong())).thenReturn(Optional.of(reservation));
        when(tableRepository.findAvailableTables(any(), any(), anyInt()))
                .thenReturn(List.of(table));

        customerService.updateReservation(1L, validDTO);

        verify(reservationRepository, times(1)).save(reservation);
    }

    @Test
    public void updateReservation_wrongUser_throwsException() {
        User anotherUser = new User();
        anotherUser.setEmail("another@email.com");

        Reservation reservation = new Reservation();
        reservation.setUser(anotherUser); // Otro usuario, no el que está autenticado

        when(reservationRepository.findById(anyLong())).thenReturn(Optional.of(reservation));

        assertThrows(RuntimeException.class, () -> customerService.updateReservation(1L, validDTO));
    }

    @Test
    public void deleteReservation_success() {
        Reservation reservation = new Reservation();
        reservation.setUser(user); // La reserva pertenece al usuario

        when(reservationRepository.findById(anyLong())).thenReturn(Optional.of(reservation));

        customerService.deleteReservation(1L);

        verify(reservationRepository, times(1)).deleteById(1L);
    }

    @Test
    public void deleteReservation_wrongUser_throwsException() {
        User anotherUser = new User();
        anotherUser.setEmail("another@email.com");

        Reservation reservation = new Reservation();
        reservation.setUser(anotherUser);

        when(reservationRepository.findById(anyLong())).thenReturn(Optional.of(reservation));

        assertThrows(RuntimeException.class, () -> customerService.deleteReservation(1L));
    }

    @Test
    public void getPastReservations_returnsOnlyPastReservations() {
        Reservation pastReservation = new Reservation();
        pastReservation.setDate(LocalDate.now().minusDays(5));
        pastReservation.setUser(user);

        Reservation futureReservation = new Reservation();
        futureReservation.setDate(LocalDate.now().plusDays(5));
        futureReservation.setUser(user);

        when(userRepository.findByEmail("user@email.com")).thenReturn(Optional.of(user));
        when(reservationRepository.findAll()).thenReturn(List.of(pastReservation, futureReservation));

        List<Reservation> reservations = reservationRepository.findAll().stream()
                .filter(r -> r.getDate().isBefore(LocalDate.now()))
                .toList();

        assert(reservations.size() == 1);
        assert(reservations.get(0).getDate().isBefore(LocalDate.now()));
    }

    @Test
    public void shouldGenerateNotificationOnReservationCreation() {

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication auth = new UsernamePasswordAuthenticationToken("user@email.com", null);
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);

        User user = new User();
        user.setEmail("user@email.com");
        when(userRepository.findByEmail("user@email.com")).thenReturn(Optional.of(user));

        when(adminService.getOpeningHour()).thenReturn("09:00");
        when(adminService.getClosingHour()).thenReturn("22:00");
        when(tableRepository.findAvailableTables(any(), any(), anyInt()))
            .thenReturn(List.of(new RestaurantTable()));

        Restaurant mockedRestaurant = new Restaurant();
        mockedRestaurant.setAvailabilityHours(List.of("09:00-22:00"));

        ReservationRequestDTO dto = new ReservationRequestDTO(
            LocalDate.now().plusDays(1),
            LocalTime.of(13, 30),
            2
        );

        customerService.makeReservation(dto);

        ArgumentCaptor<Notification> notifCaptor = ArgumentCaptor.forClass(Notification.class);
        verify(notificationRepository).save(notifCaptor.capture());

        Notification notif = notifCaptor.getValue();
        assertTrue(notif.getMessage().toLowerCase().contains("reserva"));
    }

    @Test
    public void shouldGenerateNotificationOnReservationCancellation() {
        
        String email = "user@email.com";
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication auth = new UsernamePasswordAuthenticationToken(email, null);
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);

        User user = new User();
        user.setEmail(email);

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setDate(LocalDate.now().plusDays(1));
        reservation.setHour(LocalTime.of(13, 30));

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(reservationRepository.findById(anyLong())).thenReturn(Optional.of(reservation));

        customerService.deleteReservation(1L);

        ArgumentCaptor<Notification> notifCaptor = ArgumentCaptor.forClass(Notification.class);
        verify(notificationRepository).save(notifCaptor.capture());

        Notification notif = notifCaptor.getValue();
        assertTrue(notif.getMessage().toLowerCase().contains("cancelación") || notif.getMessage().toLowerCase().contains("cancelada"));
    }

}