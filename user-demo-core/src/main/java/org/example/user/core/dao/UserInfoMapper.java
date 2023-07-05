package org.example.user.core.dao;

import org.apache.ibatis.annotations.Param;
import org.example.user.core.domain.UserInfo;

import java.util.List;

public interface UserInfoMapper {

    long count(@Param(value = "email") String email,
               @Param(value = "deleted") Boolean deleted);

    List<UserInfo> select(@Param(value = "email") String email,
                          @Param(value = "deleted") Boolean deleted,
                          @Param(value = "offset") Integer Offset,
                          @Param(value = "limit") Integer limit);

}
