package com.practice.practicemanage.service;

import com.practice.practicemanage.pojo.Menu;
import com.practice.practicemanage.pojo.Role;
import com.practice.practicemanage.pojo.User;
import com.practice.practicemanage.pojo.dto.UserLoginDto;
import com.practice.practicemanage.pojo.dto.UserDto;
import com.practice.practicemanage.repository.RoleRepository;
import com.practice.practicemanage.repository.UserRepository;
import com.practice.practicemanage.response.ResponseMessage;
import com.practice.practicemanage.service.impl.ILoginService;
import com.practice.practicemanage.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class LoginService implements ILoginService {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private LogUtil logUtil;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TypeConversionUtil typeConversionUtil;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public ResponseMessage<Object> login(UserLoginDto users) {
        String userName = users.getUserName();
        String password = users.getPassWord();
        if (StringUtil.isEmpty(userName) || StringUtil.isEmpty(password)) {
            logUtil.error(LoginService.class, "用户名或密码不能为空");
            return ResponseMessage.error("用户名或密码不能为空");
        }
        password = PasswordUtil.comparePassword(password);
        System.out.println("userName: " + userName + " password: " + password);
        User user = userService.findUser(userName, password);
        System.out.println("查询："+user);
        if (user == null) {
            logUtil.error(LoginService.class, "用户名或密码错误");
            return ResponseMessage.error("用户名或密码错误");
        }
        String token = jwtUtil.createToken(userName);
        String refreshToken = jwtUtil.createRefreshToken(userName);
        redisUtil.set("TOKEN_"+token+userName, user, 1, TimeUnit.HOURS); // 1小时过期
        redisUtil.set("REFRESHTOKEN_"+refreshToken+userName, user, 1, TimeUnit.DAYS); // 1天过期

        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("refreshToken", refreshToken);

        map.put("id", user.getId());
        map.put("userName", user.getUserName());
        map.put("passWord", user.getPassWord());
        map.put("phone", user.getPhone());
        map.put("status", user.getStatus());

        return ResponseMessage.success("登录成功", map);
    }

    @Override
    public ResponseMessage<Object> logouts(String token, String refreshToken) {
        try {
            if (redisUtil.exists("TOKEN_"+token+jwtUtil.extractUsername(token))) { // 判断是否存在
                redisUtil.delete("TOKEN_"+token+jwtUtil.extractUsername(token));
                redisUtil.delete("REFRESHTOKEN_"+refreshToken+jwtUtil.extractUsername(refreshToken));
                return ResponseMessage.success("认证令牌成功，登出成功");
            }else if (redisUtil.exists("REFRESHTOKEN_"+refreshToken+jwtUtil.extractUsername(refreshToken))) {
                redisUtil.delete("REFRESHTOKEN_"+refreshToken+jwtUtil.extractUsername(refreshToken));
                return ResponseMessage.success("认证刷新令牌成功，登出成功");
            }
            logUtil.error(LoginService.class, "无令牌退出");
            return ResponseMessage.success("令牌不存在，登出成功");
        } catch (Exception e) {
            logUtil.error(LoginService.class, "登出失败", e);
            return ResponseMessage.error("登出失败");
        }
    }

    @Override
    public ResponseMessage<Object> register(UserDto user) {
        try {
            if (userService.findUser(user.getUserName(), null) != null) {
                return ResponseMessage.error("用户名已存在");
            }

            User addUser = userService.add(user);
            if (addUser == null){
                return ResponseMessage.error("注册失败");
            }
            return ResponseMessage.success("注册成功");
        }catch (Exception e){
            logUtil.error(LoginService.class, "注册失败", e);
            return ResponseMessage.error("注册失败");
        }
    }

    @Override
    public ResponseMessage<Object> infoByToken(String token, List<Menu> menuList) {
        try {
            logUtil.info(LoginService.class, "获取用户信息");
            User user = (User) redisUtil.get("TOKEN_"+token+jwtUtil.extractUsername(token));
            if (user == null) {
                logUtil.error(LoginService.class, "用户不存在");
                return ResponseMessage.error("用户不存在");
            }
            
            // 应该只有修改个人信息时才会出现redis与数据库不一致的情况
//            Optional<User> userOptional = userRepository.findById(user.getId());
//            if (userOptional.isEmpty()) {
//                logUtil.error(LoginService.class, "用户不存在");
//                return ResponseMessage.error("用户不存在");
//            } else if (!(Objects.equals(user, typeConversionUtil.convertToClass(userOptional.get(), User.class)))) { // 判断两个对象是否相等
//                logUtil.info(LoginService.class, "redis与数据库不一致，更新redis");
//                redisUtil.set("TOKEN_"+token+jwtUtil.extractUsername(token), userOptional.get());
//                user = userOptional.get();
//            }
            
            Optional<Role> role = Optional.ofNullable(roleRepository.findByUserid(Integer.valueOf(user.getStatus())));
            Map<String, Object> map = getMap(user, role.get(), menuList);
            logUtil.info(LoginService.class, "路由:"+ menuList);

            return ResponseMessage.success("返回角色个人信息", map);
        } catch (Exception e) {
            logUtil.error(LoginService.class, "获取用户信息失败", e);
            return ResponseMessage.error("获取用户信息失败");
        }
    }

    private static Map<String, Object> getMap(User user, Role role, List<Menu> menuList) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", user.getUserName());
        map.put("phone", user.getPhone());
        switch (user.getStatus()) {
            case 1:
                map.put("avatar", "https://i.pinimg.com/1200x/8d/9f/09/8d9f095f1c59bba933ce67c7cf7fe508.jpg");
                break;
            case 2:
                map.put("avatar", "https://i.pinimg.com/736x/22/d5/2e/22d52efa636c70bd2c7cc88fbf5d7c56.jpg");
                break;
            case 3:
                map.put("avatar", "https://i.pinimg.com/736x/d3/ac/9b/d3ac9b50a6c82c97225e2f4dcb7ceb01.jpg");
                break;
            case 4:
                map.put("avatar", "https://i.pinimg.com/736x/01/d4/6d/01d46d481a4ad3776839a2ad37f134d4.jpg");
                break;
        }
        map.put("roles", role.getIdentity());
        map.put("menu", menuList);
        return map;
    }
}
