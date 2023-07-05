package org.example.user.register.service.impl;

import org.example.user.core.dao.UserMapper;
import org.example.user.core.domain.User;
import org.example.user.core.domain.UserModel;
import org.example.user.register.exception.UserDoseNotExistException;
import org.example.user.core.dao.UserModelMapper;
import org.example.user.register.service.UserModelService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * It is used to store the user information
 * @author zhixiong.huang
 */
@Service
public class UserModelServiceImpl implements UserModelService {

    private final UserModelMapper userModelMapper;

    private final UserMapper userMapper;

    public UserModelServiceImpl(UserModelMapper userModelMapper, UserMapper userMapper) {
        this.userModelMapper = userModelMapper;
        this.userMapper = userMapper;
    }

    @Override
    public UserModel query(Long userId) {
        return userModelMapper.selectByPrimaryKey(userId);
    }

    @Override
    public void delete(Long userId) {
        UserModel userModel = new UserModel();
        userModel.setUserId(userId);
        userModel.setDeleted(true);
        userModel.setGmtModified(new Date(System.currentTimeMillis()));
        userModelMapper.updateByPrimaryKeySelective(userModel);
    }

    @Override
    public UserModel create(Long userId, String firstName, String lastName, String birthdate, String gender, Integer zipcode) {
        UserModel userModel = new UserModel();
        userModel.setUserId(userId);
        userModel.setFirstName(firstName);
        userModel.setLastName(lastName);
        userModel.setBirthdate(birthdate);
        userModel.setGender(gender);
        userModel.setZipcode(zipcode);
        userModel.setGmtCreate(new Date(System.currentTimeMillis()));
        userModelMapper.insertSelective(userModel);
        return userModel;
    }

    @Override
    public UserModel update(Long userId, String firstName, String lastName, String birthdate, String gender, Integer zipcode) {
        User user = userMapper.selectByPrimaryKey(userId);
        if(user == null || user.getDeleted()){
            throw new UserDoseNotExistException();
        }
        UserModel userModel = new UserModel();
        userModel.setUserId(userId);
        userModel.setFirstName(firstName);
        userModel.setLastName(lastName);
        userModel.setBirthdate(birthdate);
        userModel.setGender(gender);
        userModel.setZipcode(zipcode);
        userModel.setGmtModified(new Date(System.currentTimeMillis()));
        userModelMapper.updateByPrimaryKeySelective(userModel);
        return userModelMapper.selectByPrimaryKey(userId);
    }
}
