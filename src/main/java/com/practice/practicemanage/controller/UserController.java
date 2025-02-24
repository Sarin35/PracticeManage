package com.practice.practicemanage.controller;

import com.practice.practicemanage.pojo.User;
import com.practice.practicemanage.pojo.dto.UserDto;
import com.practice.practicemanage.pojo.dto.UserIdDto;
import com.practice.practicemanage.response.ResponseMessage;
import com.practice.practicemanage.service.impl.IUserService;
import com.practice.practicemanage.utils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController // 接口方法可以返回对象，并且会自动转换成json
@RequestMapping("/user")
public class UserController {

//    @GetMapping：查询
//    @PostMapping：增加
//    @PutMapping：修改
//    @DeleteMapping：删除
//    @Validated 开启校验，校验失败会抛出异常，和@RequestBody 一起使用
//    @RequestBody 接收前端传来的json数据
//    @PathVariable 接收url中的参数
//    @RequestParam 接收url中的参数

//    测试
    @Autowired
    IUserService userService;
    @Autowired
    LogUtil logUtil;

    @PostMapping("/test")
    public ResponseMessage<Object> testPostMapping(@Validated @RequestBody UserDto user){
        try {
            User userPojo = userService.add(user);
            return ResponseMessage.success(userPojo);
        } catch (Exception e) {
            logUtil.error(UserController.class, "插入用户失败", e);
            return ResponseMessage.error("插入用户失败");
        }
    }
    @GetMapping("/{userId}")
    public ResponseMessage<Object> testGetMapping(@PathVariable Integer userId){
        try {
            User user = userService.getById(userId);
            return ResponseMessage.success(user);
        } catch (Exception e) {
            logUtil.error(UserController.class, "根据id查询用户失败", e);
            return ResponseMessage.error("根据id查询用户失败");
        }
    }
    @PutMapping("/testPut")
    public ResponseMessage<Object> testPutMapping(@Validated @RequestBody UserIdDto user){
        try {
            User userPojo = userService.edit(user);
            return ResponseMessage.success(null, userPojo);
        } catch (Exception e) {
            logUtil.error(UserController.class, "修改用户失败", e);
            return ResponseMessage.error("修改用户失败");
        }
    }
    @DeleteMapping("/testDelete/{userId}")
    public ResponseMessage<Object> testDeleteMapping(@PathVariable Integer userId){
        try {
            userService.updataById(userId);
            return ResponseMessage.success("删除用户成功");
        } catch (Exception e) {
            logUtil.error(UserController.class, "删除用户失败", e);
            return ResponseMessage.error("删除用户失败");
        }
    }
//    test -------------------------------------------------------------------------------------------------------------

}
