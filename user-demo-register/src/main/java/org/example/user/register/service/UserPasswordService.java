package org.example.user.register.service;

import org.example.user.register.model.ChangePasswordResponse;

public interface UserPasswordService {
    void create(Long userId, String password);

    boolean match(Long userId, String password);
    ChangePasswordResponse changePassword(Long userId, String oldPassword, String newPassword);
}
