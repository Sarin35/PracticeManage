package com.practice.practicemanage.service.userService;

import com.practice.practicemanage.pojo.User;
import com.practice.practicemanage.pojo.UserLoginDto;
import com.practice.practicemanage.pojo.dto.UserDto;
import com.practice.practicemanage.response.ResponseMessage;
import com.practice.practicemanage.utils.JwtUtil;
import com.practice.practicemanage.utils.LogUtil;
import com.practice.practicemanage.utils.RedisUtil;
import com.practice.practicemanage.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class LoginService implements ILoginService{

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private LogUtil logUtil;

    @Override
    public ResponseMessage<Object> login(UserLoginDto users) {
        String userName = users.getUserName();
        String password = users.getPassWord();
        if (StringUtil.isEmpty(userName) || StringUtil.isEmpty(password)) {
            logUtil.error(LoginService.class, "用户名或密码不能为空");
            return ResponseMessage.error("用户名或密码不能为空");
        }
        password = DigestUtils.md5DigestAsHex(password.getBytes()); // md5加密
        User user = userService.findUser(userName, password);
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
//        map.put("passWord", user.getPassWord());
        map.put("Phone", user.getPhone());
        map.put("Status", user.getStatus());

        return ResponseMessage.success("登录成功", map);
    }

    @Override
    public ResponseMessage<Object> logout(String token, String refreshToken) {
        try {
            if (redisUtil.exists("TOKEN_"+token+jwtUtil.extractUsername(token))) {
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
}
