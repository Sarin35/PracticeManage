package com.practice.practicemanage.controller;

import com.practice.practicemanage.pojo.User;
import com.practice.practicemanage.pojo.dto.UserDto;
import com.practice.practicemanage.response.ResponseMessage;
import com.practice.practicemanage.service.user.IUserService;
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
    public ResponseMessage<Object> testMapping(@RequestBody UserDto user){
        User userPojo = userService.add(user);
        return ResponseMessage.success(null, userPojo);
    }

}
