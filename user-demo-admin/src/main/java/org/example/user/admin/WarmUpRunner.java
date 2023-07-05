package org.example.user.admin;

import lombok.extern.slf4j.Slf4j;
import org.example.user.admin.controller.HealthCheckController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WarmUpRunner implements CommandLineRunner {

    @Value("${warm-up.enabled}")
    private boolean warmUpEnabled;

    @Autowired
    private HealthCheckController checkController;

    @Override
    public void run(String... args) throws Exception {

        checkController.setStatus(Status.UP);
        log.info("This application is running...");

    }
}
