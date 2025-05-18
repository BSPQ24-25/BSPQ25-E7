package com.restaurant.reservation.Unitarian;

import com.restaurant.reservation.model.Reservation;
import com.restaurant.reservation.model.Review;
import com.restaurant.reservation.model.User;
import com.restaurant.reservation.repository.ReservationRepository;
import com.restaurant.reservation.repository.ReviewRepository;
import com.restaurant.reservation.repository.UserRepository;
import com.restaurant.reservation.service.ReviewService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReviewServiceTest {

    @Mock private ReviewRepository reviewRepository;
    @Mock private ReservationRepository reservationRepository;
    @Mock private UserRepository userRepository;

    @InjectMocks private ReviewService reviewService;

    private User user;
    private Reservation reservation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        user = mock(User.class);
        when(user.getId()).thenReturn(10L);

        reservation = mock(Reservation.class);
        when(reservation.getId()).thenReturn(1L);
        when(reservation.getUser()).thenReturn(user);
        when(reservation.getDate()).thenReturn(LocalDate.now().minusDays(1));
    }

    @Test
    public void submitReview_success() {
        when(userRepository.findById(10L)).thenReturn(Optional.of(user));
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(reviewRepository.existsByReservationId(1L)).thenReturn(false);
        when(reviewRepository.save(any(Review.class))).thenAnswer(inv -> inv.getArgument(0));

        Review review = reviewService.submitReview(10L, 1L, 5, "Great experience!");

        assertNotNull(review);
        assertEquals(5, review.getRating());
        assertEquals("Great experience!", review.getComment());
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    public void submitReview_futureReservation_throwsException() {
        when(reservation.getDate()).thenReturn(LocalDate.now().plusDays(1));
        when(userRepository.findById(10L)).thenReturn(Optional.of(user));
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
            reviewService.submitReview(10L, 1L, 4, "Nice!")
        );

        assertEquals("Cannot review a future reservation.", exception.getMessage());
    }

    @Test
    public void submitReview_invalidCustomerOrReservation_throwsException() {
        when(userRepository.findById(10L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            reviewService.submitReview(10L, 1L, 3, "Ok")
        );

        assertEquals("Invalid customer or reservation ID.", exception.getMessage());
    }

    @Test
    public void submitReview_userDoesNotOwnReservation_throwsException() {
        User anotherUser = mock(User.class);
        when(anotherUser.getId()).thenReturn(99L);

        when(reservation.getUser()).thenReturn(anotherUser);
        when(userRepository.findById(10L)).thenReturn(Optional.of(user));
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
            reviewService.submitReview(10L, 1L, 4, "Good")
        );

        assertEquals("User does not own this reservation.", exception.getMessage());
    }
}