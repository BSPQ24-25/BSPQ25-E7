package com.restaurant.reservation.Performance;

import com.github.noconnor.junitperf.JUnitPerfTest;
import com.github.noconnor.junitperf.JUnitPerfTestActiveConfig;
import com.github.noconnor.junitperf.JUnitPerfInterceptor;
import com.github.noconnor.junitperf.JUnitPerfReportingConfig;
import com.github.noconnor.junitperf.JUnitPerfTestRequirement;
import com.github.noconnor.junitperf.reporting.providers.HtmlReportGenerator;
import com.restaurant.reservation.service.*;
import com.restaurant.reservation.dto.RegisterRequestDTO;
import com.restaurant.reservation.model.UserType;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(JUnitPerfInterceptor.class)
public class PerformanceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private EmailSenderService emailSenderService;

    @JUnitPerfTestActiveConfig
    private static final JUnitPerfReportingConfig PERF_CONFIG = JUnitPerfReportingConfig.builder()
        .reportGenerator(new HtmlReportGenerator(System.getProperty("user.dir") + "/target/reports/perf-report.html"))
        .build();

    @BeforeEach
    void setup() {
        // Setup global if needed
    }

    // ✅ Registration test
    @Test
    @JUnitPerfTest(threads = 10, durationMs = 5000, warmUpMs = 1000)
    @JUnitPerfTestRequirement(executionsPerSec = 2, percentiles = "95:1000ms")
    public void testRegisterUserPerformance() {
        authService.register(new RegisterRequestDTO(
            "test" + System.nanoTime() + "@mail.com", "testUser", "1234567890", "password",
            UserType.CUSTOMER));
    }

    // Make reservation test
    @Test
    @JUnitPerfTest(threads = 2, durationMs = 10000, warmUpMs = 2000)
    @JUnitPerfTestRequirement(executionsPerSec = 2, percentiles = "95:1000ms")
    public void testMakeReservationPerformance() {
        customerService.makeReservation(new com.restaurant.reservation.dto.ReservationRequestDTO(
            java.time.LocalDate.now().plusDays(1),
            java.time.LocalTime.of(12, 0),
            2
        ));
    }

    // Cancel reservation test
    @Test
    @JUnitPerfTest(threads = 2, durationMs = 8000, warmUpMs = 2000)
    @JUnitPerfTestRequirement(executionsPerSec = 2, percentiles = "95:1000ms")
    public void testCancelReservationPerformance() {
        adminService.cancelReservation(1L); // Usa IDs válidos en la base de datos
    }

    // Confirm Reservation test
    @Test
    @JUnitPerfTest(threads = 2, durationMs = 8000, warmUpMs = 2000)
    @JUnitPerfTestRequirement(executionsPerSec = 2, percentiles = "95:1000ms")
    public void testConfirmReservationPerformance() {
        adminService.confirmReservation(1L); // Usa IDs válidos en la base de datos
    }

    // ✅ Email sending test
    @Test
    @JUnitPerfTest(threads = 10, durationMs = 5000, warmUpMs = 1000)
    @JUnitPerfTestRequirement(executionsPerSec = 2, percentiles = "95:1000ms")
    public void testSendEmailPerformance() {
        emailSenderService.sendEmail(
            "test@example.com",
            "Performance Test",
            "<h1>This is a performance test email.</h1>"
        );
    }

    // Review Service Test
    @Test
    @JUnitPerfTest(threads = 2, durationMs = 7000, warmUpMs = 2000)
    @JUnitPerfTestRequirement(executionsPerSec = 2, percentiles = "95:1000ms")
    public void testSubmitReviewPerformance() {
        reviewService.submitReview(1L, 1L, 5, "Excellent service!"); // Usa IDs válidos
    }
}