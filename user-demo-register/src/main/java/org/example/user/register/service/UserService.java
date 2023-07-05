package org.example.user.register.service;

import org.example.user.core.domain.User;
import org.example.user.register.model.EmailCheckResult;
import org.example.user.register.model.UserResponse;

import java.util.List;

public interface UserService {
    List<User> query(String email);
    UserResponse create(String email, String password, String firstName, String lastName, String birthdate, String gender, Integer zipcode);
    EmailCheckResult checkEmail(String mail);
    UserResponse login(String email, String password);

}
