package com.practice.practicemanage.controller;

import com.practice.practicemanage.pojo.Menu;
import com.practice.practicemanage.pojo.dto.TokenDto;
import com.practice.practicemanage.pojo.dto.UserLoginDto;
import com.practice.practicemanage.pojo.dto.UserRegisterDto;
import com.practice.practicemanage.response.ResponseMessage;
import com.practice.practicemanage.service.impl.LoginServiceImpl;
import com.practice.practicemanage.service.impl.MenuServiceImpl;
import com.practice.practicemanage.utils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class LoginController {

    @Autowired
    private LogUtil logUtil;
    @Autowired
    private LoginServiceImpl loginService;
    @Autowired
    private MenuServiceImpl menuService;


    @PostMapping("/login")
    public ResponseMessage<Object> login(@Validated @RequestBody UserLoginDto user){
        return loginService.login(user);
    }

//    @PostMapping("/logout")
//    public ResponseMessage<Object> logOut(@RequestHeader("Authorization") String token, @RequestHeader("refreshAuthorization") String refreshToken){
//        return loginService.logout(token, refreshToken);
//    }

    @PostMapping("/register")
    public ResponseMessage<Object> register(@Validated @RequestBody UserRegisterDto user){
        System.out.println("user->>>: " + user);
        return loginService.register(user);
    }

    @PostMapping("/info")
    public ResponseMessage<Object> infoByToken(@Validated @RequestBody TokenDto token){
        logUtil.info(LoginController.class, "获取用户信息and菜单信息infoByToken");
        List<Menu> menuList = menuService.getMenuList();
        return loginService.infoByToken(token.getToken(), menuList);
    }

    @PostMapping("/getScoolORUnitList")
    public ResponseMessage<Object> getScoolORUnitList(@RequestBody Map<String, Integer> map){
        return loginService.getScoolORUnitList(map.get("type"));
    }
}
