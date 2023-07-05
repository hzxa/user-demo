package org.example.user.register.service.impl;

import org.example.user.core.dao.UserPasswordMapper;
import org.example.user.core.domain.UserPassword;
import org.example.user.core.domain.UserPasswordExample;
import org.example.user.register.exception.UserDoseNotExistException;
import org.example.user.register.model.ChangePasswordResponse;
import org.example.user.register.model.ErrorCode;
import org.example.user.register.service.EncryptionService;
import org.example.user.register.service.UserPasswordService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserPasswordServiceImpl implements UserPasswordService {

    private final UserPasswordMapper userPasswordMapper;

    private final EncryptionService encryptionService;

    public UserPasswordServiceImpl(UserPasswordMapper userPasswordMapper,
                                   EncryptionService encryptionService) {
        this.userPasswordMapper = userPasswordMapper;
        this.encryptionService = encryptionService;
    }

    @Override
    public void create(Long userId, String password) {
        UserPassword record = new UserPassword();
        record.setUserId(userId);
        record.setGmtCreate(new Date(System.currentTimeMillis()));
        record.setHashCode(encryptionService.bcryptEncrypt(password));
        userPasswordMapper.insertSelective(record);
    }

    @Override
    public boolean match(Long userId, String password) {
        UserPasswordExample example = new UserPasswordExample();
        example.createCriteria().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        UserPassword userPassword = userPasswordMapper.selectByPrimaryKey(userId);
        if(userPassword == null || userPassword.getDeleted()){
            throw new UserDoseNotExistException();
        }
        return encryptionService.bcryptMatch(password, userPassword.getHashCode());
    }

    @Override
    public ChangePasswordResponse changePassword(Long userId, String oldPassword, String newPassword) {

        if(!match(userId, oldPassword)){
            return ChangePasswordResponse.builder()
                    .code(ErrorCode.USER_PASSWORD_INVALID.getCode())
                    .msg(ErrorCode.USER_PASSWORD_INVALID.getMsg())
                    .build();
        }
        UserPassword record = new UserPassword();
        record.setUserId(userId);
        record.setGmtModified(new Date(System.currentTimeMillis()));
        record.setHashCode(encryptionService.bcryptEncrypt(newPassword));
        userPasswordMapper.updateByPrimaryKeySelective(record);

        return ChangePasswordResponse.builder()
                .code(0)
                .build();
    }
}
