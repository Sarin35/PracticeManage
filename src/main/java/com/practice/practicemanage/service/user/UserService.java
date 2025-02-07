package com.practice.practicemanage.service.user;

import com.practice.practicemanage.pojo.User;
import com.practice.practicemanage.pojo.dto.UserDto;
import com.practice.practicemanage.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // 配置为 spring 的 bean
public class UserService implements IUserService {

//    自动装配 repository
    @Autowired
    UserRepository userRepository;

    @Override
    public User add(UserDto user) {

        User userPojo = new User();

        BeanUtils.copyProperties(user, userPojo);

        return userRepository.save(userPojo);
//        调用数据访问类
    }
}
