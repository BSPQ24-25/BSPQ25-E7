package com.restaurant.reservation.service;

// Añadir estos imports:
import com.restaurant.reservation.dto.ReservationRequestDTO;
import com.restaurant.reservation.model.Reservation;
import com.restaurant.reservation.model.RestaurantTable;
import com.restaurant.reservation.model.User;
import com.restaurant.reservation.repository.ReservationRepository;
import com.restaurant.reservation.repository.RestaurantTableRepository;
import com.restaurant.reservation.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CustomerService {
    
    @Autowired
    private ReservationRepository reservationRepository;
    
    @Autowired
    private RestaurantTableRepository tableRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Transactional
    public void makeReservation(ReservationRequestDTO reservationDTO) {
        
    
        List<RestaurantTable> tables = tableRepository.findAvailableTables(
            reservationDTO.getDate(),
            reservationDTO.getHour(),
            reservationDTO.getnPeople()
        );
    
        if (tables.isEmpty()) {
            throw new RuntimeException("No hay mesas disponibles");
        }
    
        RestaurantTable table = tables.get(0); // Primera mesa válida
    
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("Email autenticado: " + email);
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setTable(table);
        reservation.setDate(reservationDTO.getDate());
        reservation.setHour(reservationDTO.getHour());
        reservation.setnPeople(reservationDTO.getnPeople());
        reservation.setState("confirmed");
    
        System.out.println("→ Se va a guardar la reserva para el usuario: " + user.getEmail());
        reservationRepository.save(reservation);
        System.out.println("✔️ Reserva guardada correctamente");
    }    
}