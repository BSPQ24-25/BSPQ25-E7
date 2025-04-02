package com.restaurant.reservation.controller;

import com.restaurant.reservation.dto.ReservationDTO;
import com.restaurant.reservation.model.Reservation;
import com.restaurant.reservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    // Endpoint para hacer una reserva
    @PostMapping("/create")
    public Reservation createReservation(@RequestBody ReservationDTO reservationDTO) {
        return reservationService.createReservation(reservationDTO);
    }

    // Endpoint para obtener todas las reservas de un cliente
    @GetMapping("/customer/{customerId}")
    public List<Reservation> getReservationsByCustomer(@PathVariable Long customerId) {
        return reservationService.getReservationsByCustomer(customerId);
    }
}
