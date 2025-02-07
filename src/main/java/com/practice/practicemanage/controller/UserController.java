package com.practice.practicemanage.controller;

import com.practice.practicemanage.pojo.dto.UserDto;
import com.practice.practicemanage.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController // 接口方法可以返回对象，并且会自动转换成json
@RequestMapping("/user")
public class UserController {

//    @GetMapping：查询
//    @PostMapping：增加
//    @PutMapping：修改
//    @DeleteMapping：删除

//    测试
    @Autowired
    IUserService userService;

    @PostMapping("/test")
    public String testMapping(@RequestBody UserDto user){
        userService.add(user);
        return "testOk";
    }

}
