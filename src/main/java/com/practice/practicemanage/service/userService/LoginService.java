package com.practice.practicemanage.service.userService;

import com.practice.practicemanage.pojo.User;
import com.practice.practicemanage.pojo.UserLoginDto;
import com.practice.practicemanage.response.ResponseMessage;
import com.practice.practicemanage.utils.JwtUtil;
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

    @Override
    public ResponseMessage<Object> login(UserLoginDto users) {
        String userName = users.getUserName();
        String password = users.getPassWord();
        if (StringUtil.isEmpty(userName) || StringUtil.isEmpty(password)) {
            return ResponseMessage.error("用户名或密码不能为空");
        }
        password = DigestUtils.md5DigestAsHex(password.getBytes()); // md5加密
        User user = userService.findUser(userName, password);
        if (user == null) {
            return ResponseMessage.error("用户名或密码错误");
        }
        String token = jwtUtil.createToken(userName);
        String refreshToken = jwtUtil.createRefreshToken(userName);
        redisUtil.set(token+userName, user, 1, TimeUnit.HOURS); // 1小时过期
        redisUtil.set(refreshToken+userName, user, 1, TimeUnit.DAYS); // 1天过期

        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        map.put("refreshToken", refreshToken);

        return ResponseMessage.success("登录成功", map);
    }
}
