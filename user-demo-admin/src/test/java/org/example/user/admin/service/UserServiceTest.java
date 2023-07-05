package org.example.user.admin.service;

import org.example.user.admin.model.UserInfoResponse;
import org.example.user.admin.service.impl.UserServiceImpl;
import org.example.user.core.dao.*;
import org.example.user.core.domain.UserInfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class UserServiceTest {
    private UserInfoMapper userInfoMapper;

    private UserWelcomeMailSentMapper mailSentMapper;

    private UserMapper userMapper;

    private UserPasswordMapper userPasswordMapper;

    private UserModelMapper userModelMapper;

    private UserService userService;

    @Before
    public void setup(){
        userInfoMapper = Mockito.mock(UserInfoMapper.class);
        mailSentMapper = Mockito.mock(UserWelcomeMailSentMapper.class);
        userMapper = Mockito.mock(UserMapper.class);
        userPasswordMapper = Mockito.mock(UserPasswordMapper.class);
        userModelMapper = Mockito.mock(UserModelMapper.class);
        userService = new UserServiceImpl(userInfoMapper,mailSentMapper,userMapper,userPasswordMapper,userModelMapper);
    }
    @Test
    public void testList(){
        String testMail = "test@test.com";
        Integer pn = 1, ps = 10;

        List<UserInfo> users = new ArrayList<>();
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(1L);
        userInfo.setEmail("test@test.com");
        Mockito.when(userInfoMapper.count(testMail, false)).thenReturn(1L);
        Mockito.when(userInfoMapper.select(testMail, false, (pn-1)*ps, ps)).thenReturn(users);
        UserInfoResponse response = userService.list(testMail, false, pn, ps);

        Assert.assertNotNull(response);
        Assert.assertEquals(1L, (long)response.getTotal());
        Assert.assertArrayEquals(users.toArray(), response.getUsers().toArray());

    }
}
