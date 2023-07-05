package org.example.user.admin.controller;

import io.swagger.annotations.Api;
import org.springframework.boot.actuate.health.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicReference;

@RestController
@Api(tags = {"Health Check Endpoint"})
public class HealthCheckController {

    private final AtomicReference<Status> statusHolder = new AtomicReference<>(Status.DOWN);

    @GetMapping(path = "/health_check")
    public ResponseEntity<String> healthCheck() {
        Status status = statusHolder.get();
        if (status == Status.UP) {
            return ResponseEntity.ok(status.getCode());
        }
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(status.getCode());
    }

    public void setStatus(Status value) {
        this.statusHolder.set(value);
    }
}
