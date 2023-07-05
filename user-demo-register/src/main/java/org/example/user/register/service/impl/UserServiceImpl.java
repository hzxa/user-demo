package org.example.user.register.service.impl;

import org.example.user.core.dao.UserMapper;
import org.example.user.core.domain.User;
import org.example.user.core.domain.UserExample;
import org.example.user.core.domain.UserModel;
import org.example.user.register.exception.UserDoseNotExistException;
import org.example.user.register.exception.UserPasswordDoseNotMatchException;
import org.example.user.register.model.EmailCheckResult;
import org.example.user.register.model.ErrorCode;
import org.example.user.register.model.UserResponse;
import org.example.user.register.service.EmailSenderService;
import org.example.user.register.service.UserModelService;
import org.example.user.register.service.UserPasswordService;
import org.example.user.register.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final UserPasswordService passwordService;

    private final UserModelService userModelService;

    private final EmailSenderService emailSenderService;

    public UserServiceImpl(UserMapper userMapper,
                           UserPasswordService passwordService,
                           UserModelService userModelService,
                           EmailSenderService emailSenderService) {
        this.userMapper = userMapper;
        this.passwordService = passwordService;
        this.userModelService = userModelService;
        this.emailSenderService = emailSenderService;
    }

    @Override
    public List<User> query(String email) {
        UserExample example = new UserExample();
        example.createCriteria().andEmailEqualTo(email).andDeletedEqualTo(false);
        List<User> users = userMapper.selectByExample(example);
        return users;
    }

    @Override
    @Transactional
    public UserResponse create(String email, String password, String firstName, String lastName, String birthdate, String gender, Integer zipcode) {
        User user = new User();
        user.setEmail(email);
        user.setGmtCreate(new Date(System.currentTimeMillis()));
        userMapper.insertSelective(user);

        passwordService.create(user.getId(), password);
        userModelService.create(user.getId(),firstName,lastName, birthdate, gender,zipcode);
        emailSenderService.sendWelcomeEmail(user.getId(),email,firstName);

        return UserResponse.builder()
                .userId(user.getId())
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .birthdate(birthdate)
                .gender(gender)
                .zipcode(zipcode)
                .build();
    }

    @Override
    public EmailCheckResult checkEmail(String email) {
        UserExample example = new UserExample();
        example.createCriteria().andEmailEqualTo(email);
        List<User> users = userMapper.selectByExample(example);
        if(users != null && users.size()>0){
            if(!users.get(0).getDeleted()){
                return EmailCheckResult.builder()
                        .code(ErrorCode.EMAIL_EXISTED.getCode())
                        .msg(ErrorCode.EMAIL_EXISTED.getMsg())
                        .build();
            }else{
                return EmailCheckResult.builder()
                        .code(ErrorCode.EMAIL_FORBIDDEN.getCode())
                        .msg(ErrorCode.EMAIL_FORBIDDEN.getMsg())
                        .build();
            }
        }
        return EmailCheckResult.builder()
                .code(0).build();
    }

    @Override
    public UserResponse login(String email, String password) {
        UserExample example = new UserExample();
        example.createCriteria().andEmailEqualTo(email).andDeletedEqualTo(false);
        List<User> users = userMapper.selectByExample(example);
        if(users.size() ==0){
            throw new UserDoseNotExistException();
        }
        boolean passwordMatch = passwordService.match(users.get(0).getId(), password);
        if(!passwordMatch){
            throw new UserPasswordDoseNotMatchException();
        }
        UserModel userModel = userModelService.query(users.get(0).getId());

        return UserResponse.builder()
                .userId(users.get(0).getId())
                .email(users.get(0).getEmail())
                .firstName(userModel.getFirstName())
                .lastName(userModel.getLastName())
                .birthdate(userModel.getBirthdate())
                .gender(userModel.getGender())
                .zipcode(userModel.getZipcode())
                .build();
    }
}
