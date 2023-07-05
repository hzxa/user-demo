package org.example.user.admin.service.impl;

import org.example.user.admin.model.UserWelcomeMail;
import org.example.user.core.dao.*;
import org.example.user.admin.model.UserInfoResponse;
import org.example.user.admin.service.UserService;
import org.example.user.core.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserInfoMapper userInfoMapper;

    private final UserWelcomeMailSentMapper mailSentMapper;

    private final UserMapper userMapper;

    private final UserPasswordMapper userPasswordMapper;

    private final UserModelMapper userModelMapper;

    public UserServiceImpl(UserInfoMapper userInfoMapper,
                           UserWelcomeMailSentMapper mailSentMapper,
                           UserMapper userMapper,
                           UserPasswordMapper userPasswordMapper,
                           UserModelMapper userModelMapper) {
        this.userInfoMapper = userInfoMapper;
        this.mailSentMapper = mailSentMapper;
        this.userMapper = userMapper;
        this.userPasswordMapper = userPasswordMapper;
        this.userModelMapper = userModelMapper;
    }

    @Override
    public UserInfoResponse list(String email, Boolean deleted, Integer pn, Integer ps) {
        long total = userInfoMapper.count(email, deleted);
        UserInfoResponse ret = UserInfoResponse.builder()
                .total(total)
                .build();
        if(total>0){
            Integer pageNo = pn==0 ? pn: pn-1;
            ret.setUsers(userInfoMapper.select(email, deleted,pageNo*ps, ps));
        }
        return ret;
    }

    @Transactional
    @Override
    public void delete(List<Long> userIds) {
        for(Long userId : userIds){
            deleteUser(userId);
            deleteUserPassword(userId);
            deleteUserModel(userId);
        }
    }

    @Override
    public UserWelcomeMail welcomeEmail(Long userId) {
        UserWelcomeMailSent mailSent = mailSentMapper.selectByPrimaryKey(userId);
        if(mailSent != null){
            return UserWelcomeMail.builder()
                    .userId(userId)
                    .path(mailSent.getMailPath())
                    .content(mailSent.getMailContent())
                    .sentDate(mailSent.getcTime())
                    .build();
        }
        return null;
    }

    private int deleteUser(Long userId){
        User row = new User();
        row.setId(userId);
        row.setDeleted(true);
        row.setGmtModified(new Date(System.currentTimeMillis()));
        return userMapper.updateByPrimaryKeySelective(row);
    }

    private int deleteUserPassword(Long userId){
        UserPassword row = new UserPassword();
        row.setUserId(userId);
        row.setDeleted(true);
        row.setGmtModified(new Date(System.currentTimeMillis()));
        return userPasswordMapper.updateByPrimaryKeySelective(row);
    }

    private int deleteUserModel(Long userId){
        UserModel row = new UserModel();
        row.setUserId(userId);
        row.setDeleted(true);
        row.setGmtModified(new Date(System.currentTimeMillis()));
        return userModelMapper.updateByPrimaryKeySelective(row);
    }
}
