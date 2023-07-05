package org.example.user.register.service;

import org.example.user.core.dao.UserMapper;
import org.example.user.core.domain.User;
import org.example.user.core.domain.UserExample;
import org.example.user.core.domain.UserModel;
import org.example.user.register.model.EmailCheckResult;
import org.example.user.register.model.ErrorCode;
import org.example.user.register.model.UserResponse;
import org.example.user.register.service.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class UserServiceTest {

    private UserMapper userMapper;

    private UserPasswordService passwordService;

    private UserModelService userModelService;

    private EmailSenderService emailSenderService;

    private UserService userService;

    @Before
    public void setup(){

        userMapper = Mockito.mock(UserMapper.class);
        passwordService = Mockito.mock(UserPasswordService.class);
        userModelService = Mockito.mock(UserModelService.class);
        emailSenderService = Mockito.mock(EmailSenderService.class);

        userService = new UserServiceImpl(userMapper, passwordService, userModelService, emailSenderService);

    }


    @Test
    public void testQuery(){
        List<User> users = new ArrayList<>();
        User user = new User();
        user.setEmail("test.com");
        user.setId(1L);
        users.add(user);
        UserExample example = new UserExample();
        example.createCriteria().andEmailEqualTo(user.getEmail());
        Mockito.when(userMapper.selectByExample(Mockito.any())).thenReturn(users);

        List<User> result = userService.query("test.com");
        Assert.assertEquals(result.size(), 1);
        Assert.assertArrayEquals(users.toArray(), result.toArray());

    }

    @Test
    public void testCreate(){

        Mockito.when(userMapper.insertSelective(Mockito.any())).thenReturn(1);

        Mockito.doNothing().when(passwordService).create(Mockito.any(), Mockito.any());
        Mockito.when(userModelService.create(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(new UserModel());
        Mockito.doNothing().when(emailSenderService).sendWelcomeEmail(Mockito.any(),Mockito.any(),Mockito.any());
        String email="test.com";
        String password="1234";
        String firstname= "test";
        String lastName = "last";
        String birthdate = "2020202";
        String gender = "male";
        Integer zipcode = 10000;

        UserResponse userResponse = userService.create(email, password, firstname,lastName,birthdate, gender,zipcode);

        Assert.assertNotNull(userResponse);
        Assert.assertEquals(userResponse.getEmail(), email);
        Assert.assertEquals(userResponse.getFirstName(), firstname);
        Assert.assertEquals(userResponse.getLastName(), lastName);
        Assert.assertEquals(userResponse.getBirthdate(), birthdate);
        Assert.assertEquals(userResponse.getGender(), gender);
        Assert.assertEquals(userResponse.getZipcode(), zipcode);
    }

    @Test
    public void testCheckEmail(){
        String testEmail = "test2@testcom";
        User user = new User();
        user.setEmail(testEmail);
        user.setId(1L);
        user.setDeleted(false);
        List<User> users = new ArrayList<>();
        users.add(user);

        Mockito.when(userMapper.selectByExample(Mockito.any())).thenReturn(users);


        EmailCheckResult result = userService.checkEmail(testEmail);
        Assert.assertNotNull(result);
        Assert.assertEquals((long)result.getCode(), ErrorCode.EMAIL_EXISTED.getCode());
        Assert.assertEquals(result.getMsg(), ErrorCode.EMAIL_EXISTED.getMsg());

        testEmail = "test3@test.com";
        User deletedUser = new User();
        deletedUser.setEmail(testEmail);
        deletedUser.setDeleted(true);
        List<User> deletedUsers = new ArrayList<>();
        deletedUsers.add(deletedUser);
        Mockito.when(userMapper.selectByExample(Mockito.any())).thenReturn(deletedUsers);
        result = userService.checkEmail(testEmail);
        Assert.assertNotNull(result);
        Assert.assertEquals((long)result.getCode(), ErrorCode.EMAIL_FORBIDDEN.getCode());
        Assert.assertEquals(result.getMsg(), ErrorCode.EMAIL_FORBIDDEN.getMsg());

        Mockito.when(userMapper.selectByExample(Mockito.any())).thenReturn(new ArrayList<User>());
        result = userService.checkEmail(testEmail);
        Assert.assertNotNull(result);
        Assert.assertEquals((long)result.getCode(), 0L);

    }
}
