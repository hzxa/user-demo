package org.example.user.register.service;

import org.example.user.core.domain.UserModel;

public interface UserModelService {
    UserModel query(Long userId);
    void delete(Long userId);

    UserModel create(Long userId, String firstName, String lastName, String birthdate, String gender, Integer zipcode);

    UserModel update(Long userId, String firstName, String lastName, String birthdate, String gender, Integer zipcode);


}
