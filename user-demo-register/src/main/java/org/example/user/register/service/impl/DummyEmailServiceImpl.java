package org.example.user.register.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.user.register.service.EmailService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DummyEmailServiceImpl implements EmailService {

    @Override
    public void send(String emailAddress, String subject, String content) {
        log.info("mail send to {} successfully", emailAddress);
    }
}
