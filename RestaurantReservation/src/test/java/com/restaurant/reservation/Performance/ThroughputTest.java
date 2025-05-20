package com.restaurant.reservation.Performance;

import com.github.noconnor.junitperf.*;
import com.github.noconnor.junitperf.reporting.providers.HtmlReportGenerator;
import com.restaurant.reservation.service.*;
import com.restaurant.reservation.dto.RegisterRequestDTO;
import com.restaurant.reservation.model.UserType;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(JUnitPerfInterceptor.class)
public class ThroughputTest {

    @Autowired
    private AuthService authService;

    @JUnitPerfTestActiveConfig
    private static final JUnitPerfReportingConfig CONFIG = JUnitPerfReportingConfig.builder()
        .reportGenerator(new HtmlReportGenerator(System.getProperty("user.dir") + "/target/reports/throughput-report.html"))
        .build();

    @Test
    @JUnitPerfTest(threads = 10, durationMs = 5000)
    @JUnitPerfTestRequirement(executionsPerSec = 5) // solo mide throughput
    public void testRegisterThroughput() {
        authService.register(new RegisterRequestDTO(
            "test" + System.nanoTime() + "@mail.com", "testUser", "1234567890", "password", UserType.CUSTOMER));
    }
}
