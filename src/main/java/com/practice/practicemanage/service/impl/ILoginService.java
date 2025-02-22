package com.practice.practicemanage.service.impl;

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
    ResponseMessage<Object> logouts(String token, String refreshToken);

    /**
     * 注册
     * @param user
     * @return
     */
    ResponseMessage<Object> register(UserDto user);

    /**
     * 获取用户信息
     * @param token
     * @return
     */
    ResponseMessage<Object> infoByToken(String token);
}
