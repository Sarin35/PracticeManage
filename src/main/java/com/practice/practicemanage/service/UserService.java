package com.practice.practicemanage.service;

import com.practice.practicemanage.pojo.dto.UserDto;
import org.springframework.stereotype.Service;

@Service // 配置为 spring 的 bean
public class UserService implements IUserService {

    @Override
    public void add(UserDto user) {
//        调用数据访问类

    }
}
