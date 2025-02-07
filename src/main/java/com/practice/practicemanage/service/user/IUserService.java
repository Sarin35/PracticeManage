package com.practice.practicemanage.service.user;

import com.practice.practicemanage.pojo.User;
import com.practice.practicemanage.pojo.dto.UserDto;

public interface IUserService {

    /**
     * @param user
     * @return
     * @deprecated 插入用户 测试
     */
    User add(UserDto user);
}
