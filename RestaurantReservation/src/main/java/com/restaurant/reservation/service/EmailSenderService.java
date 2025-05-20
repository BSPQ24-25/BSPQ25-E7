package com.restaurant.reservation.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * @brief Envía un correo electrónico HTML.
     * @param to Dirección de destino.
     * @param subject Asunto del correo.
     * @param htmlContent Contenido en formato HTML.
     */

    public void sendEmail(String to, String subject, String htmlContent) {
        System.out.println("[EmailSenderService] Preparando email...");
        System.out.println("A: " + to);
        System.out.println("Asunto: " + subject);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("cosanostra.software.restaurante@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // true indica que es HTML

            System.out.println("[EmailSenderService] Enviando mensaje...");
            mailSender.send(message);
            System.out.println("[EmailSenderService] ✔️ Email enviado correctamente");
        } catch (MessagingException e) {
            System.out.println("[EmailSenderService] ❌ Error enviando email: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
