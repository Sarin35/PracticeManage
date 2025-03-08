package com.practice.practicemanage.service;

import com.practice.practicemanage.pojo.*;
import com.practice.practicemanage.pojo.dto.UserDto;
import com.practice.practicemanage.pojo.dto.UserLoginDto;
import com.practice.practicemanage.pojo.dto.UserRegisterDto;
import com.practice.practicemanage.repository.*;
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
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private TypeConversionUtil typeConversionUtil;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private StudentInfoService studentInfoService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StudentInfoRepository studentInfoRepository;
    @Autowired
    private TeacherInfoRepository teacherInfoRepository;
    @Autowired
    private UnitUserRepository unitUserRepository;
    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private UnitRepository unitRepository;

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
        if (user == null || user.getStatus() == 0) {
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

        Role role = roleRepository.findByUserid(Integer.valueOf(user.getStatus()));
        map.put("roles", role.getIdentity());

        return ResponseMessage.success("登录成功", map);
    }

//    @Autowired
//    private TeacherInfoRepository teacherInfoRepository;
//    @Autowired
//    private UnitUserRepository unitUserRepository;


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
    public ResponseMessage<Object> register(UserRegisterDto user) {
        try {
            if (userRepository.findByUserName(user.getUserName()) != null) {
                System.out.println("用户名已存在");
                return ResponseMessage.error("用户名已存在");
            }
            if (userRepository.findByPhone(user.getPhone()) != null) {
                System.out.println("手机号已存在");
                return ResponseMessage.error("手机号已存在");
            }
//            保存用户信息（登录用）
            System.out.println("addAll");
            addAll(user);
//            根据身份写入不同表
            if (user.getStatus() == '1' || user.getStatus() == 1) {
//                保存学生信息
                System.out.println("studentInfo11111");
                StudentInfo studentInfo = new StudentInfo();
                studentInfo.setPhone(user.getPhone());
                studentInfo.setSchool(user.getSchool());
                studentInfo.setStatus((byte) 1);
                studentInfoRepository.save(studentInfo);

            } else if (user.getStatus() == '2' || user.getStatus() == 2) {
                System.out.println("teacherInfo22222");
                TeacherInfo teacherInfo = new TeacherInfo();
                teacherInfo.setPhone(user.getPhone());
                teacherInfo.setStatus((byte) 1);
                teacherInfo.setSchool(user.getSchool());
                teacherInfoRepository.save(teacherInfo);

            } else if (user.getStatus() == '3' || user.getStatus() == 3) {
                System.out.println("unitUser33333");
                UnitUser unitUser = new UnitUser();
                unitUser.setPhone(user.getPhone());
                unitUser.setStatus((byte) 1);
                unitUser.setName(user.getSchool());
                unitUserRepository.save(unitUser);

            }

//            User addUser = userService.add(user);
//            if (addUser == null){
//                return ResponseMessage.error("注册失败");
//            }
            return ResponseMessage.success("注册成功");
        }catch (Exception e){
            logUtil.error(LoginService.class, "注册失败", e);
            return ResponseMessage.error("注册失败");
        }
    }

    public void addAll(UserRegisterDto user) {
//        保存用户账号密码（登录用）
        User users = new User();
        users.setUserName(user.getUserName());
        users.setPassWord(PasswordUtil.comparePassword(user.getPassWord()));
        users.setPhone(user.getPhone());
        users.setStatus(user.getStatus());
        userRepository.save(users);
    }

    @Override
    public ResponseMessage<Object> infoByToken(String token, List<Menu> menuList) {
        try {
            logUtil.info(LoginService.class, "获取用户信息");
            User user = (User) redisUtil.get("REFRESHTOKEN_"+token+jwtUtil.extractUsername(token));
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
            
            Role role = roleRepository.findByUserid(Integer.valueOf(user.getStatus()));
            if (Objects.equals(role.getIdentity(), "STUDENT")) {

                StudentInfo teacherPhone = (StudentInfo) studentInfoService.getTeacherPhoneByStudentPhone(user.getPhone());
                Map<String, Object> map = getMap(user, role, menuList, teacherPhone.getTeacherPhone());
                logUtil.info(LoginService.class, "路由:"+ menuList);
                return ResponseMessage.success("返回角色个人信息", map);

            } else if (Objects.equals(role.getIdentity(), "TEACHER") || Objects.equals(role.getIdentity(), "UNIT") || Objects.equals(role.getIdentity(), "ADMINISTRATOR")) {

                Map<String, Object> map = getMap(user, role, menuList, "NotTeacher");
                logUtil.info(LoginService.class, "路由:"+ menuList);
                return ResponseMessage.success("返回角色个人信息", map);

            } else {
                logUtil.error(LoginService.class, "该用户无角色");
                return ResponseMessage.error("该用户无角色");
            }

        } catch (Exception e) {
            logUtil.error(LoginService.class, "获取用户信息失败", e);
            return ResponseMessage.error("获取用户信息失败");
        }
    }

    @Override
    public ResponseMessage<Object> getScoolORUnitList(Integer type) {
        try {
            if (type == 1 || type == 2) {
                List<School> schools = schoolRepository.findByStatus((byte) 1);
                if (schools.isEmpty()) {
                    return ResponseMessage.error("获取学校信息失败");
                }
                return ResponseMessage.success("获取学校信息成功", schools);
            } else if (type == 3) {
                List<Unit> units = unitRepository.findByStatus((byte) 1);
                if (units.isEmpty()) {
                    return ResponseMessage.error("获取单位信息失败");
                }
                return ResponseMessage.success("获取单位信息成功", units);
            } else {
                return ResponseMessage.success("注册管理员，无需获取", null);
            }
        } catch (Exception e) {
            logUtil.error(LoginService.class, "获取信息失败", e);
            return ResponseMessage.error("获取信息失败");
        }
    }

    private static Map<String, Object> getMap(User user, Role role, List<Menu> menuList, String teacherPhone) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", user.getUserName());
        map.put("phone", user.getPhone());
        map.put("teacherphone", teacherPhone);
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
