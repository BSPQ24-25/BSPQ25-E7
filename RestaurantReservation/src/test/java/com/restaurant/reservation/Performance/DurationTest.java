package com.restaurant.reservation.Performance;

import com.github.noconnor.junitperf.*;
import com.github.noconnor.junitperf.reporting.providers.HtmlReportGenerator;
import com.restaurant.reservation.service.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(JUnitPerfInterceptor.class)
public class DurationTest {

    @Autowired
    private EmailSenderService emailSenderService;

    @JUnitPerfTestActiveConfig
    private static final JUnitPerfReportingConfig CONFIG = JUnitPerfReportingConfig.builder()
        .reportGenerator(new HtmlReportGenerator(System.getProperty("user.dir") + "/target/reports/duration-report.html"))
        .build();

    @Test
    @JUnitPerfTest(threads = 5, durationMs = 7000)
    @JUnitPerfTestRequirement(percentiles = "95:800ms") // solo mide duración
    public void testSendEmailDuration() {
        emailSenderService.sendEmail(
            "test@example.com",
            "Performance Duration Test",
            "<h1>Email test</h1>");
    }
}
