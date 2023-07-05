package org.example.user.register.service;

import org.example.user.core.dao.UserPasswordMapper;
import org.example.user.core.domain.UserPassword;
import org.example.user.register.service.impl.EncryptionServiceImpl;
import org.example.user.register.service.impl.UserPasswordServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class UserPasswordServiceTest {

    private UserPasswordMapper userPasswordMapper;

    private EncryptionService encryptionService;

    private UserPasswordService userPasswordService;

    @Before
    public void setup(){
        encryptionService = new EncryptionServiceImpl();
        userPasswordMapper = Mockito.mock(UserPasswordMapper.class);
        userPasswordService = new UserPasswordServiceImpl(userPasswordMapper, encryptionService);
    }

    @Test
    public void testMatch(){
        UserPassword userPassword = new UserPassword();
        Long userId = 1000L;
        String password = "password";
        userPassword.setUserId(userId);
        userPassword.setDeleted(false);
        userPassword.setHashCode(encryptionService.bcryptEncrypt(password));
        Mockito.when(userPasswordMapper.selectByPrimaryKey(userId)).thenReturn(userPassword);

        boolean result = userPasswordService.match(userId, password);

        Assert.assertTrue(result);

    }
}
