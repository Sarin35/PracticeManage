package com.practice.practicemanage.service.userService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.practicemanage.pojo.User;
import com.practice.practicemanage.pojo.dto.UserDto;
import com.practice.practicemanage.pojo.dto.UserIdDto;
import com.practice.practicemanage.repository.RoleRepository;
import com.practice.practicemanage.repository.UserRepository;
import com.practice.practicemanage.security.CustomUserDetails;
import com.practice.practicemanage.utils.JwtUtil;
import com.practice.practicemanage.utils.LogUtil;
import com.practice.practicemanage.utils.RedisUtil;
import com.practice.practicemanage.utils.TypeConversionUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // 配置为 spring 的 bean
@CacheConfig(cacheNames = "user") // 配置缓存
public class UserService implements IUserService {

//    自动装配 repository
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LogUtil logUtil;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private TypeConversionUtil typeConversionUtil;

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

    @Override
    public User getUserByToken(String token) {

        String userName = jwtUtil.extractUsername(token);
        Object userObject =  redisUtil.get(token + userName);
//        是否为空
        if (userObject == null){
           return null;
        }

//        如果是Json字符串，转换成user
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (userObject instanceof String userJson) {
                return objectMapper.readValue(userJson, User.class);
            } else if (userObject instanceof User) {
                return (User) userObject;
            } else {
                return null;
            }
        } catch (JsonMappingException e) {
            logUtil.error(UserService.class, "转换类型置空", e);
            return null;
        } catch (JsonProcessingException e) {
            logUtil.error(UserService.class, "JsonProcessing异常", e);
            return null;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String token, String userName) throws UsernameNotFoundException {
        try {
            Object userObject = redisUtil.get(token + userName);

            if (userObject == null) {

                User user = userRepository.findByUserName(userName);
                return new CustomUserDetails(user, roleRepository);

            } else {
                return new CustomUserDetails(typeConversionUtil.convertToClass(userObject, User.class), roleRepository);
            }

        } catch (UsernameNotFoundException e) {
            logUtil.error(UserService.class, "查询用户UserName失败", e);
            return null;
        }
    }

}
