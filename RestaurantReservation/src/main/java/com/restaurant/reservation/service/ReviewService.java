package com.restaurant.reservation.service;

import com.restaurant.reservation.model.Review;
import com.restaurant.reservation.model.Reservation;
import com.restaurant.reservation.model.User;
import com.restaurant.reservation.repository.ReviewRepository;
import com.restaurant.reservation.repository.ReservationRepository;
import com.restaurant.reservation.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Review submitReview(Long customerId, Long reservationId, int rating, String comment) {
        Optional<User> customerOpt = userRepository.findById(customerId);
        Optional<Reservation> reservationOpt = reservationRepository.findById(reservationId);

        if (customerOpt.isEmpty() || reservationOpt.isEmpty()) {
            throw new IllegalArgumentException("Invalid customer or reservation ID.");
        }

        Reservation reservation = reservationOpt.get();

        // Check that the reservation is completed and the customer owns it
        if (!reservation.getUser().getId().equals(customerId)) {
            throw new IllegalStateException("User does not own this reservation.");
        }

        if (!reservation.getDate().isBefore(LocalDate.now())) {
            throw new IllegalStateException("Cannot review a future reservation.");
        }

        if (reviewRepository.existsByReservationId(reservationId)) {
            throw new IllegalStateException("Reservation has already been reviewed.");
        }

        Review review = new Review(customerOpt.get(), reservation, rating, comment);
        return reviewRepository.save(review);
    }

    public List<Review> getReviewsByCustomer(Long customerId) {
        return reviewRepository.findByCustomerId(customerId);
    }

    public List<Review> getReviewsByRestaurant(Long restaurantId) {
        return reviewRepository.findByRestaurantId(restaurantId);
    }

    public boolean hasReservationBeenReviewed(Long reservationId) {
        return reviewRepository.existsByReservationId(reservationId);
    }
}