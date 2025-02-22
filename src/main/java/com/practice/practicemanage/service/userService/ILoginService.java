package com.practice.practicemanage.service.userService;

import com.practice.practicemanage.pojo.UserLoginDto;
import com.practice.practicemanage.pojo.dto.UserDto;
import com.practice.practicemanage.response.ResponseMessage;

public interface ILoginService {

    /**
     * 登录
     * @param user
     * @return
     */
    ResponseMessage<Object> login(UserLoginDto user);

    /**
     * 登出
     * @param token
     * @param refreshToken
     * @return
     */
    ResponseMessage<Object> logout(String token, String refreshToken);

    /**
     * 注册
     * @param user
     * @return
     */
    ResponseMessage<Object> register(UserDto user);
}
