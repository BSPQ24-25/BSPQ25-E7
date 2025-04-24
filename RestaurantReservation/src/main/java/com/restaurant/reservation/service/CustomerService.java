package com.restaurant.reservation.service;

// Añadir estos imports:
import com.restaurant.reservation.dto.ReservationRequestDTO;
import com.restaurant.reservation.exception.InvalidReservationTimeException;
import com.restaurant.reservation.exception.NoTableWithEnoughCapacityException;
import com.restaurant.reservation.exception.PastDateReservationException;
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

import java.time.LocalDate;
import java.time.LocalTime;
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

        LocalDate reservationDate = reservationDTO.getDate();
        LocalTime reservationHour = reservationDTO.getHour();
        int numberOfPeople = reservationDTO.getnPeople();

        // 🚫 No se permite reservar en fechas pasadas
        if (reservationDate.isBefore(LocalDate.now())) {
            throw new PastDateReservationException();
        }

        // 🕒 Horario permitido del restaurante: 12:00 - 23:00
        LocalTime openingTime = LocalTime.of(12, 0);
        LocalTime closingTime = LocalTime.of(23, 0);
        if (reservationHour.isBefore(openingTime) || reservationHour.isAfter(closingTime)) {
            throw new InvalidReservationTimeException();
        }

        // 🔍 Buscar mesas disponibles con capacidad suficiente
        List<RestaurantTable> tables = tableRepository.findAvailableTables(
            reservationDate,
            reservationHour,
            numberOfPeople
        );

        if (tables.isEmpty()) {
            throw new NoTableWithEnoughCapacityException(numberOfPeople);
        }

        // 👤 Obtener el usuario autenticado
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 🪑 Seleccionar primera mesa válida y crear la reserva
        RestaurantTable selectedTable = tables.get(0);
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setTable(selectedTable);
        reservation.setDate(reservationDate);
        reservation.setHour(reservationHour);
        reservation.setnPeople(numberOfPeople);
        reservation.setState("confirmed");

        reservationRepository.save(reservation);

        System.out.println("✔️ Reserva creada para " + user.getEmail() +
                       " el " + reservationDate + " a las " + reservationHour +
                       " para " + numberOfPeople + " personas.");
    }    
}