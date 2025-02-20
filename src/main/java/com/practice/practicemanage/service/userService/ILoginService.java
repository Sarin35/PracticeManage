package com.practice.practicemanage.service.userService;

import com.practice.practicemanage.pojo.UserLoginDto;
import com.practice.practicemanage.response.ResponseMessage;

public interface ILoginService {

    ResponseMessage<Object> login(UserLoginDto user);
}
