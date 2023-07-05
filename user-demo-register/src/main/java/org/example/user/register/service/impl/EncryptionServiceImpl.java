package org.example.user.register.service.impl;

import org.example.user.register.service.EncryptionService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EncryptionServiceImpl implements EncryptionService {

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public String bcryptEncrypt(String input) {
        return passwordEncoder.encode(input);
    }

    @Override
    public boolean bcryptMatch(String rawInput, String encryptedInput) {
        return passwordEncoder.matches(rawInput, encryptedInput);
    }
}
