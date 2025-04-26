package com.restaurant.reservation.controller;

import com.restaurant.reservation.dto.ReservationRequestDTO;
import com.restaurant.reservation.model.Reservation;
import com.restaurant.reservation.model.User;
import com.restaurant.reservation.repository.ReservationRepository;
import com.restaurant.reservation.repository.UserRepository;
import com.restaurant.reservation.service.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired  
    private UserRepository userRepository;

    @Autowired 
    private ReservationRepository reservationRepository;

    // Show list of reservations of the user
    @GetMapping("/reservations")
    public String getReservations(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            model.addAttribute("reservations", List.of());
            model.addAttribute("errorMessage", "Usuario autenticado pero no encontrado en la base de datos.");
            return "customer/reservations";
        }

        User user = optionalUser.get();
        List<Reservation> reservations = reservationRepository.findByUser(user);
        model.addAttribute("reservations", reservations);
        return "customer/reservations";
    }

    // Show the form of a new reservation
    @GetMapping("/new-reservation")
    public String showReservationForm(Model model) {
        model.addAttribute("reservation", new ReservationRequestDTO());
        return "customer/reservation";
    }

    // Process the form of reservation
    @PostMapping("/make-reservation")
    public String makeReservation(@ModelAttribute ReservationRequestDTO reservationDTO, Model model) {
        System.out.println("[Controller] Recibida solicitud de reserva");
        System.out.println("Fecha: " + reservationDTO.getDate());
        System.out.println("Hora: " + reservationDTO.getHour());
        System.out.println("Personas: " + reservationDTO.getnPeople());

        try {
            customerService.makeReservation(reservationDTO);
            System.out.println("[Controller] Reserva creada correctamente.");
            return "redirect:/customer/reservations?success=true";
        } catch (RuntimeException e) {
            System.out.println("[Controller] Error al crear la reserva: " + e.getMessage());
            model.addAttribute("reservation", reservationDTO);
            model.addAttribute("errorMessage", e.getMessage());
            return "customer/reservation"; // Vuelve al formulario con mensaje de error
        }
    }


    @PutMapping("/reservations/{id}")
    @ResponseBody
    public String updateReservation(@PathVariable Long id, @RequestBody ReservationRequestDTO dto) {
        try {
            customerService.updateReservation(id, dto);
            return "Reserva actualizada con éxito";
        } catch (RuntimeException e) {
            return "Error al actualizar reserva: " + e.getMessage();
        }
    }

    @DeleteMapping("/reservations/{id}")
    @ResponseBody
    public String deleteReservation(@PathVariable Long id) {
        try {
            customerService.deleteReservation(id);
            return "Reserva eliminada con éxito";
        } catch (RuntimeException e) {
            return "Error al eliminar reserva: " + e.getMessage();
        }
    }

}