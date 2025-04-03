package com.restaurant.reservation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom("your-email@example.com");

        emailSender.send(message);

        System.out.println("Email sent successfully");
    }

    public void sendOrderConfirmationEmail(String to, String orderDetails) {
        String subject = "Order Confirmation";
        String text = "Thank you for your order. Here are the details:\n" + orderDetails;
        sendEmail(to, subject, text);
    }
}

