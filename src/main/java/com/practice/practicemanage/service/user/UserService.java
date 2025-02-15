package com.practice.practicemanage.service.user;

import com.practice.practicemanage.pojo.User;
import com.practice.practicemanage.pojo.dto.UserDto;
import com.practice.practicemanage.pojo.dto.UserIdDto;
import com.practice.practicemanage.repository.UserRepository;
import com.practice.practicemanage.util.LogUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // 配置为 spring 的 bean
@CacheConfig(cacheNames = "user") // 配置缓存
public class UserService implements IUserService {

//    自动装配 repository
    @Autowired
    UserRepository userRepository;
    @Autowired
    LogUtil logUtil;

    //    test -------------------------------------------------------------------------------------------------------------

    @Override
    @Cacheable(value = "userList")
    public List<User> userList() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            logUtil.error(UserService.class, "查询所有用户失败", e);
            return null;
        }
    }

    @Override
    @Cacheable(value = "user", key = "#userId")
    public User getById(Integer userId) {
        try {
            return  userRepository.findById(userId).orElse(null);
        } catch (Exception e) {
            logUtil.error(UserService.class, "根据id查询用户失败", e);
            return null;
        }
    }

    @Override
    @CachePut(cacheNames = "user", key = "#result.id")
    public User add(UserDto user) {

        try {
            User userPojo = new User();
            BeanUtils.copyProperties(user, userPojo);
            return userRepository.save(userPojo);
        }catch (Exception e){
            logUtil.error(UserService.class, "插入用户失败", e);
            return null;
        }
//        调用数据访问类
    }

    @Override
    @CachePut(cacheNames = "user", key = "#user.id")
    public User edit(UserIdDto user) {

        try {
            User userPojo = new User();

            BeanUtils.copyProperties(user, userPojo);

            return userRepository.save(userPojo);
        }catch (Exception e){
            logUtil.error(UserService.class, "修改用户失败", e);
            return null;
        }
    }

    @Override
    @CachePut(cacheNames = "user", key = "#userId")
    public Optional<User> updataById(Integer userId) {
        try {
            userRepository.updateStatusToZeroById(userId);
            return userRepository.findById(userId);
        } catch (Exception e) {
            logUtil.error(UserService.class, "删除用户失败", e);
            return Optional.empty();
        }
    }

//    test -------------------------------------------------------------------------------------------------------------
}
