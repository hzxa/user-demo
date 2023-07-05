package org.example.user.register.service;

public interface EmailSenderService {
    void sendWelcomeEmail(Long userId, String email, String firstName);
}
