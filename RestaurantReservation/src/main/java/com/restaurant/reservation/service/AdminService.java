package com.restaurant.reservation.service;

import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restaurant.reservation.model.Reservation;
import com.restaurant.reservation.repository.NotificationRepository;
import com.restaurant.reservation.repository.ReservationRepository;
import com.restaurant.reservation.model.Notification;

/**
 * @class AdminService
 * @brief Servicio para gestionar funciones administrativas del restaurante
 */
@Service
public class AdminService {

    private final ReservationRepository reservationRepository;
    private final EmailSenderService emailSenderService;
    private final NotificationRepository notificationRepository;

    private String openingHour = "09:00";
    private String closingHour = "22:00";

    /**
     * @brief Constructor que inyecta dependencias necesarias.
     * @param reservationRepository Repositorio de reservas
     * @param emailSenderService Servicio de envío de emails
     * @param notificationRepository Repositorio de notificaciones
     */
    public AdminService(ReservationRepository reservationRepository, EmailSenderService emailSenderService, NotificationRepository notificationRepository) {
        this.reservationRepository = reservationRepository;
        this.emailSenderService = emailSenderService;
        this.notificationRepository = notificationRepository;
    }

    /**
     * @brief Obtiene la hora de apertura del restaurante como cadena.
     * @return Hora de apertura en formato HH:mm
     */
    public String getOpeningHour() {
        return openingHour;
    }

    /**
     * @brief Establece la hora de apertura del restaurante.
     * @param openingHour Hora en formato HH:mm
     */
    public void setOpeningHour(String openingHour) {
        this.openingHour = openingHour;
    }

    /**
     * @brief Obtiene la hora de cierre del restaurante como cadena.
     * @return Hora de cierre en formato HH:mm
     */
    public String getClosingHour() {
        return closingHour;
    }

    /**
     * @brief Establece la hora de cierre del restaurante.
     * @param closingHour Hora en formato HH:mm
     */
    public void setClosingHour(String closingHour) {
        this.closingHour = closingHour;
    }

    /**
     * @brief Convierte la hora de apertura a objeto LocalTime.
     * @return Hora de apertura como LocalTime
     */
    public LocalTime getOpeningHourAsTime() {
        return LocalTime.parse(openingHour);
    }

    /**
     * @brief Convierte la hora de cierre a objeto LocalTime.
     * @return Hora de cierre como LocalTime
     */
    public LocalTime getClosingHourAsTime() {
        return LocalTime.parse(closingHour);
    }

    /**
     * @brief Retorna todas las reservas incluyendo la información del usuario.
     * @return Lista de reservas
     */
    public List<Reservation> findAllReservations() {
        return reservationRepository.findAllWithUser();
    }

    /**
     * @brief Cancela una reserva específica y notifica por email al usuario.
     * @param id ID de la reserva a cancelar
     */
    @Transactional
    public void cancelReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Reservation not found"));

        reservation.setState("cancelled");
        reservationRepository.save(reservation);

        String userEmail = reservation.getUser().getEmail();
        String htmlContent = loadTemplate("templates/email/cancellation_email.html");
        emailSenderService.sendEmail(userEmail, "Reservation Cancelled", htmlContent);
    }

    /**
     * @brief Confirma una reserva específica y notifica por email al usuario.
     * @param id ID de la reserva a confirmar
     */
    @Transactional
    public void confirmReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Reservation not found"));

        reservation.setState("confirmed");
        reservationRepository.save(reservation);

        String userEmail = reservation.getUser().getEmail();
        String htmlContent = loadTemplate("templates/email/confirmation_email.html");
        emailSenderService.sendEmail(userEmail, "Reservation Confirmed", htmlContent);
    }

    /**
     * @brief Marca todas las notificaciones como vistas.
     */
    @Transactional
    public void markAllNotificationsAsSeen() {
        notificationRepository.markAllAsSeen(); 
    }

    /**
     * @brief Devuelve las notificaciones no leídas.
     * @return Lista de notificaciones no vistas
     */
    public List<Notification> getUnreadNotifications() {
        return notificationRepository.findBySeenFalse();
    }

    /**
     * @brief Carga una plantilla HTML desde el classpath.
     * @param path Ruta del archivo de plantilla
     * @return Contenido de la plantilla como String
     * @throws RuntimeException si falla la lectura
     */
    private String loadTemplate(String path) {
        try {
            return new String(
                getClass().getClassLoader().getResourceAsStream(path).readAllBytes(),
                StandardCharsets.UTF_8
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to load email template: " + path, e);
        }
    }
}