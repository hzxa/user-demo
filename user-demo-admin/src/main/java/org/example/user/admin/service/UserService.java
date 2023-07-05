package org.example.user.admin.service;

import org.example.user.admin.model.UserInfoResponse;
import org.example.user.admin.model.UserWelcomeMail;

import java.util.List;

public interface UserService {

    UserInfoResponse list(String email, Boolean deleted, Integer pn, Integer ps);

    void delete(List<Long> userIds);

    UserWelcomeMail welcomeEmail(Long userId);
}
