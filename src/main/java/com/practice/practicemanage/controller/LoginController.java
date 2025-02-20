package com.practice.practicemanage.controller;

import com.practice.practicemanage.pojo.UserLoginDto;
import com.practice.practicemanage.response.ResponseMessage;
import com.practice.practicemanage.service.userService.LoginService;
import com.practice.practicemanage.utils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class LoginController {

    @Autowired
    private LogUtil logUtil;
    @Autowired
    private LoginService loginService;


    @PostMapping("/login")
    public ResponseMessage<Object> login(@Validated @RequestBody UserLoginDto user){
        return loginService.login(user);
    }
}
