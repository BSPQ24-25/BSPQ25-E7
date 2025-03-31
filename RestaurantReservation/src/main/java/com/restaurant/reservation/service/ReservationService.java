package com.restaurant.reservation.service;

import com.restaurant.reservation.model.Customer;
import com.restaurant.reservation.model.Reservation;
import com.restaurant.reservation.model.RestaurantTable;
import com.restaurant.reservation.dto.ReservationDTO;
import com.restaurant.reservation.repository.CustomerRepository;
import com.restaurant.reservation.repository.TableRepository;
import com.restaurant.reservation.repository.UserRepository;
import com.restaurant.reservation.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final CustomerRepository customerRepository;
    private final TableRepository tableRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, 
                              CustomerRepository customerRepository, 
                              TableRepository tableRepository) {
        this.reservationRepository = reservationRepository;
        this.customerRepository = customerRepository;
        this.tableRepository = tableRepository;
    }

    public Reservation createReservation(ReservationDTO reservationDTO) {
        // Obtener el Customer desde el customerId
        Customer customer = customerRepository.findById(reservationDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // Obtener la Table desde el tableId
        RestaurantTable table = tableRepository.findById(reservationDTO.getTableId())
                .orElseThrow(() -> new RuntimeException("Table not found"));

        // Crear y configurar la nueva reserva
        Reservation reservation = new Reservation();
        reservation.setCustomer(customer);  // Aquí pasas el objeto Customer completo
        reservation.setTable(table);  // Aquí pasas el objeto Table completo
        reservation.setDate(reservationDTO.getDate());
        reservation.setHour(reservationDTO.getHour());
        reservation.setnPeople(reservationDTO.getnPeople());
        
        // Guardar la reserva en la base de datos
        return reservationRepository.save(reservation);
    }

    // Método para obtener todas las reservas de un cliente
    public List<Reservation> getReservationsByCustomer(Long customerId) {
        return reservationRepository.findByCustomerId(customerId);
    }
}
