package com.practice.practicemanage.service.user;

import com.practice.practicemanage.pojo.User;
import com.practice.practicemanage.pojo.dto.UserDto;
import com.practice.practicemanage.pojo.dto.UserIdDto;

public interface IUserService {

    /**
     * @deprecated 插入用户 测试
     * @param user
     * @return User
     */
    User add(UserDto user);

    /**
     * @deprecated 根据id查询用户 测试
     * @param userId
     * @return User
     */
    User getById(Integer userId);

    /**
     * @deprecated 修改用户 测试
     * @param user
     * @return
     */
    User edit(UserIdDto user);

    /**
     * @deprecated 删除用户 测试
     * @param userId
     * @return
     */
    int updataById(Integer userId);

//    test -------------------------------------------------------------------------------------------------------------
}
