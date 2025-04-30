
package com.restaurant.reservation.Unitarian;

import com.restaurant.reservation.service.EmailSenderService;

import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailSenderServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private MimeMessage mimeMessage;

    @InjectMocks
    private EmailSenderService emailSenderService;

    private String to;
    private String subject;
    private String htmlContent;

    @BeforeEach
    void setUp() {
        to = "test@example.com";
        subject = "Test Subject";
        htmlContent = "<h1>This is a test email.</h1>";
    }

    @Test
    public void shouldSendHtmlEmailSuccessfully() {
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        emailSenderService.sendEmail(to, subject, htmlContent);

        verify(mailSender, times(1)).send(mimeMessage);
    }
}
