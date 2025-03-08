package com.practice.practicemanage.service.impl;

import com.practice.practicemanage.pojo.Menu;
import com.practice.practicemanage.pojo.dto.UserDto;
import com.practice.practicemanage.pojo.dto.UserLoginDto;
import com.practice.practicemanage.pojo.dto.UserRegisterDto;
import com.practice.practicemanage.response.ResponseMessage;

import java.util.List;

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
    ResponseMessage<Object> register(UserRegisterDto user);

    /**
     * 获取用户信息
     *
     * @param token
     * @param menuList
     * @return
     */
    ResponseMessage<Object> infoByToken(String token, List<Menu> menuList);

    ResponseMessage<Object> getScoolORUnitList(Integer type);
}
