package com.restaurant.reservation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String text) {
        System.out.println("[EmailSenderService] Preparando email...");
        System.out.println("A: " + to);
        System.out.println("Asunto: " + subject);
        System.out.println("Cuerpo: " + text);

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("cosanostra.software.restaurante@gmail.com"); 
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            System.out.println("[EmailSenderService] Enviando mensaje...");
            mailSender.send(message);
            System.out.println("[EmailSenderService] ✔️ Email enviado correctamente");
        } catch (Exception e) {
            System.out.println("[EmailSenderService] ❌ Error enviando email: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
