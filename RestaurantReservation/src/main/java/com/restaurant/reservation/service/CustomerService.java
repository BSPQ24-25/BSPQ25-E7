package com.restaurant.reservation.service;

import com.restaurant.reservation.dto.ReservationRequestDTO;
import com.restaurant.reservation.exception.InvalidReservationTimeException;
import com.restaurant.reservation.exception.NoTableWithEnoughCapacityException;
import com.restaurant.reservation.exception.PastDateReservationException;
import com.restaurant.reservation.model.Reservation;
import com.restaurant.reservation.model.RestaurantTable;
import com.restaurant.reservation.model.User;
import com.restaurant.reservation.model.Notification;
import com.restaurant.reservation.repository.ReservationRepository;
import com.restaurant.reservation.repository.NotificationRepository;
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

    @Autowired
    private AdminService adminService;

    @Autowired
    private NotificationRepository notificationRepository;

    @Transactional
    public void makeReservation(ReservationRequestDTO reservationDTO) {
        LocalDate reservationDate = reservationDTO.getDate();
        LocalTime reservationHour = reservationDTO.getHour();
        int numberOfPeople = reservationDTO.getnPeople();

        // ✅ Validar que la hora es en intervalos de 30 minutos
        if (!reservationDTO.isValidTime()) {
            throw new InvalidReservationTimeException("Las reservas deben ser en horas completas o medias horas (ej: 12:00 o 12:30)");
        }

        // 🚫 No reservar fechas pasadas
        if (reservationDate.isBefore(LocalDate.now())) {
            throw new PastDateReservationException();
        }

        // 🕒 Verificar horario permitido del restaurante
        LocalTime openingTime = LocalTime.parse(adminService.getOpeningHour());
        LocalTime closingTime = LocalTime.parse(adminService.getClosingHour());
        
        if (reservationHour.isBefore(openingTime) || reservationHour.isAfter(closingTime)) {
            throw new InvalidReservationTimeException();
        }

        // 🔍 Verificar que la hora concreta tenga disponibilidad
        if (!isTimeSlotAvailable(reservationDate, reservationHour)) {
            throw new InvalidReservationTimeException("La hora seleccionada no está disponible");
        }

        // 🔎 Buscar mesas disponibles con capacidad suficiente
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

        // 🪑 Crear la reserva
        RestaurantTable selectedTable = tables.get(0);
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setTable(selectedTable);
        reservation.setDate(reservationDate);
        reservation.setHour(reservationHour);
        reservation.setnPeople(numberOfPeople);
        reservation.setState("pending");

        reservationRepository.save(reservation);

        System.out.println("✔️ Reserva creada para " + user.getEmail() +
                           " el " + reservationDate + " a las " + reservationHour +
                           " para " + numberOfPeople + " personas.");
        
                           
        notificationRepository.save(new Notification("Nueva reserva de " + user.getEmail()));

    }

    @Transactional
    public void updateReservation(Long id, ReservationRequestDTO dto) {
        Reservation reservation = reservationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        // ✅ Validar que la reserva pertenece al usuario autenticado
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!reservation.getUser().getEmail().equals(email)) {
            throw new RuntimeException("No tienes permiso para modificar esta reserva");
        }

        // ✅ Validaciones similares a creación
        if (!dto.isValidTime()) {
            throw new InvalidReservationTimeException("Las reservas deben ser en horas completas o medias horas (ej: 12:00 o 12:30)");
        }
        
        if (dto.getDate().isBefore(LocalDate.now())) {
            throw new PastDateReservationException();
        }
        
        LocalTime openingTime = LocalTime.parse(adminService.getOpeningHour());
        LocalTime closingTime = LocalTime.parse(adminService.getClosingHour());
        if (dto.getHour().isBefore(openingTime) || dto.getHour().isAfter(closingTime)) {
            throw new InvalidReservationTimeException("La hora está fuera del horario permitido");
        }

        if (!isTimeSlotAvailable(dto.getDate(), dto.getHour())) {
            throw new InvalidReservationTimeException("La hora seleccionada no está disponible");
        }

        // 🔎 Buscar mesas disponibles
        List<RestaurantTable> tables = tableRepository.findAvailableTables(
            dto.getDate(),
            dto.getHour(),
            dto.getnPeople()
        );

        if (tables.isEmpty()) {
            throw new NoTableWithEnoughCapacityException(dto.getnPeople());
        }

        // ✏️ Actualizar datos de la reserva
        reservation.setTable(tables.get(0));
        reservation.setDate(dto.getDate());
        reservation.setHour(dto.getHour());
        reservation.setnPeople(dto.getnPeople());
        reservation.setState("updated");

        reservationRepository.save(reservation);
    }

    @Transactional
    public void deleteReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!reservation.getUser().getEmail().equals(email)) {
            throw new RuntimeException("No tienes permiso para eliminar esta reserva");
        }

        notificationRepository.save(new Notification("Reserva cancelada por " + reservation.getUser().getEmail()));

        reservationRepository.deleteById(id);
    }

    // Métodos auxiliares
    private boolean isTimeSlotAvailable(LocalDate date, LocalTime hour) {
        // 🔢 Contar reservas existentes para esa fecha y hora
        long existingReservations = reservationRepository.countByDateAndHour(date, hour);

        // 🧮 Obtener capacidad total del restaurante
        int totalCapacity = getTotalRestaurantCapacity();

        return existingReservations < totalCapacity;
    }

    private int getTotalRestaurantCapacity() {
        // 🛠️ Esto se puede adaptar a tu modelo real
        return 100; // Ejemplo de capacidad máxima del restaurante
    }
}
