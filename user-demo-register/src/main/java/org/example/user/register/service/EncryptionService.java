package org.example.user.register.service;

public interface EncryptionService {
    public String bcryptEncrypt(String input);

    public boolean bcryptMatch(String rawInput, String encryptedInput);

}
