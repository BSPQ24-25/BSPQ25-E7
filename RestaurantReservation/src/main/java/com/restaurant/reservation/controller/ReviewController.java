package com.restaurant.reservation.controller;

import com.restaurant.reservation.dto.ReviewRequestDTO;
import com.restaurant.reservation.dto.ReviewResponseDTO;
import com.restaurant.reservation.model.Review;
import com.restaurant.reservation.service.ReviewService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller to handle review submission and retrieval.
 */
@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    /**
     * Submits a new review for a completed reservation.
     * @param reviewRequest the DTO containing review data
     * @return the created review response or an error message
     */
    @PostMapping
    public ResponseEntity<?> submitReview(@RequestBody @Valid ReviewRequestDTO reviewRequest) {
        try {
            Review review = reviewService.submitReview(
                reviewRequest.getCustomerId(),
                reviewRequest.getReservationId(),
                reviewRequest.getRating(),
                reviewRequest.getComment()
            );

            ReviewResponseDTO responseDTO = new ReviewResponseDTO(
                review.getReviewId(),
                review.getReservation().getReservationId(),
                review.getRating(),
                review.getComment()
            );

            return ResponseEntity.ok(responseDTO);

        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Retrieves all reviews submitted by a specific customer.
     * @param customerId ID of the customer
     * @return list of reviews
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Review>> getReviewsByCustomer(@PathVariable Long customerId) {
        List<Review> reviews = reviewService.getReviewsByCustomer(customerId);
        return ResponseEntity.ok(reviews);
    }

    /**
     * Retrieves all reviews related to a specific restaurant.
     * @param restaurantId ID of the restaurant
     * @return list of reviews
     */
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Review>> getReviewsByRestaurant(@PathVariable Long restaurantId) {
        List<Review> reviews = reviewService.getReviewsByRestaurant(restaurantId);
        return ResponseEntity.ok(reviews);
    }
}