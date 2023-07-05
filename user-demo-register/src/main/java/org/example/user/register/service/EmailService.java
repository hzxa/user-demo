package org.example.user.register.service;

public interface EmailService {

    public void send(String emailAddress, String subject, String content);
}
