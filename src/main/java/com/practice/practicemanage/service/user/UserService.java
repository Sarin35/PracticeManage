package com.practice.practicemanage.service.user;

import com.practice.practicemanage.pojo.User;
import com.practice.practicemanage.pojo.dto.UserDto;
import com.practice.practicemanage.pojo.dto.UserIdDto;
import com.practice.practicemanage.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // 配置为 spring 的 bean
public class UserService implements IUserService {

//    自动装配 repository
    @Autowired
    UserRepository userRepository;

//    test -------------------------------------------------------------------------------------------------------------
    @Override
    public User add(UserDto user) {

        User userPojo = new User();

        BeanUtils.copyProperties(user, userPojo);

        return userRepository.save(userPojo);
//        调用数据访问类
    }

    @Override
    public User getById(Integer userId) {
        return  userRepository.findById(userId).orElse(null);
    }

    @Override
    public User edit(UserIdDto user) {

        User userPojo = new User();
        BeanUtils.copyProperties(user, userPojo);

        return userRepository.save(userPojo);
    }

    @Override
    public int updataById(Integer userId) {
        return userRepository.updateStatusToZeroById(userId);
    }

//    test -------------------------------------------------------------------------------------------------------------
}
