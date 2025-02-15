package com.practice.practicemanage.service.user;

import com.practice.practicemanage.pojo.User;
import com.practice.practicemanage.pojo.dto.UserDto;
import com.practice.practicemanage.pojo.dto.UserIdDto;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    /**
     *  查询用户 测试
     * @return List<User>
     */
    List<User> userList();

    /**
     *  插入用户 测试
     * @return User
     */
    User add(UserDto user);

    /**
     *  根据id查询用户 测试
     * @return User
     */
    User getById(Integer userId);

    /**
     *  修改用户 测试
     * @return User
     */
    User edit(UserIdDto user);

    /**
     *  删除用户 测试
     * @return User
     */
    Optional<User> updataById(Integer userId);

//    test -------------------------------------------------------------------------------------------------------------
}
