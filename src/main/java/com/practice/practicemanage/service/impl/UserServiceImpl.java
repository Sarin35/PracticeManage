package com.practice.practicemanage.service.impl;

import com.practice.practicemanage.pojo.*;
import com.practice.practicemanage.pojo.dto.UserDto;
import com.practice.practicemanage.pojo.dto.UserIdDto;
import com.practice.practicemanage.repository.*;
import com.practice.practicemanage.response.ResponseMessage;
import com.practice.practicemanage.security.CustomUserDetails;
import com.practice.practicemanage.service.UserService;
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
import org.springframework.util.DigestUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service // 配置为 spring 的 bean
@CacheConfig(cacheNames = "user") // 配置缓存
public class UserServiceImpl implements UserService {

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
    @Autowired
    private StudentInfoRepository studentInfoRepository;
    @Autowired
    private TeacherInfoRepository teacherInfoRepository;
    @Autowired
    private UnitUserRepository unitUserRepository;
    @Autowired
    private AnnouncementRepository announcementRepository;

    //    test -------------------------------------------------------------------------------------------------------------

    @Cacheable(value = "userList")
    @Override
    public List<User> userList() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            logUtil.error(UserServiceImpl.class, "查询所有用户失败", e);
            return null;
        }
    }

    @Override
    @Cacheable(value = "user", key = "#userId")
    public User getById(Integer userId) {
        try {
            return  userRepository.findById(userId).orElse(null);
        } catch (Exception e) {
            logUtil.error(UserServiceImpl.class, "根据id查询用户失败", e);
            return null;
        }
    }

    @Override
//    @CachePut(cacheNames = "user", key = "#result.id") // 更新缓存
    public User add(UserDto user) {

        try {
            User userPojo = new User();
            BeanUtils.copyProperties(user, userPojo);
            userPojo.setId(null);
            userPojo.setPassWord(DigestUtils.md5DigestAsHex(user.getPassWord().getBytes())); // md5加密
            return userRepository.save(userPojo);
        }catch (Exception e){
            logUtil.error(UserServiceImpl.class, "插入用户失败", e);
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
            logUtil.error(UserServiceImpl.class, "修改用户失败", e);
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
            logUtil.error(UserServiceImpl.class, "删除用户失败", e);
            return Optional.empty();
        }
    }

    //    test -------------------------------------------------------------------------------------------------------------

    @Override
    public User getUserByToken(String head, String token) {

        String userName = jwtUtil.extractUsername(token);
        User userObject =  (User) redisUtil.get(head+token + userName);
        if (userObject == null) {
            User userObjects = new User();
            userObjects.setUserName("NotName");
            return userObjects;
        }

//        从数据库中查询用户，判断redis与数据库是否一致，不一致则更新redis，但只有修改个人信息时才会出现这种情况
//        Optional<User> userOptional = userRepository.findById(userObject.getId());
////        是否为空
//        if (userOptional.isEmpty()){
//           return null;
//        } else if (!(Objects.equals(userObject, typeConversionUtil.convertToClass(userOptional.get(), User.class)))) { // 判断两个对象是否相等
//            logUtil.info(LoginService.class, "redis与数据库不一致，更新redis");
//            redisUtil.set("TOKEN_"+token+jwtUtil.extractUsername(token), userOptional.get());
//            userObject = userOptional.get();
//        }

        return userObject;

//        如果是Json字符串，转换成user
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            if (userObject instanceof String userJson) {
//                return objectMapper.readValue(userJson, User.class);
//            } else if (userObject instanceof User) {
//                return (User) userObject;
//            } else {
//                return null;
//            }
//        } catch (JsonMappingException e) {
//            logUtil.error(UserService.class, "转换类型置空", e);
//            return null;
//        } catch (JsonProcessingException e) {
//            logUtil.error(UserService.class, "JsonProcessing异常", e);
//            return null;
//        }
    }

    @Override
    public UserDetails loadUserByUsername(String head, String token, String userName) throws UsernameNotFoundException {
        try {
            Object userObject = redisUtil.get( head + token + userName );

            if (userObject == null) {

                User user = userRepository.findByUserName(userName);
                return new CustomUserDetails(user, roleRepository);

            } else {
                return new CustomUserDetails(typeConversionUtil.convertToClass(userObject, User.class), roleRepository);
            }

        } catch (UsernameNotFoundException e) {
            logUtil.error(UserServiceImpl.class, "查询用户UserName失败", e);
            return null;
        }
    }

    @Override
    public User findUser(String userName, String password) {
        try {
            System.out.println("userName: " + userName + " password: " + password);
            return userRepository.findByUserNameAndPassWord(userName, password);
        } catch (Exception e) {
            logUtil.error(UserServiceImpl.class, "查询用户失败", e);
            return null;
        }
    }

//    管理员部分

    @Override
    public ResponseMessage<Object> getRoles() {
        try {
            Collection<Byte> status = Arrays.asList((byte) 4, (byte) 5);
            List<User> userL = userRepository.findByStatusIn(status);
            if (userL == null) {
                return ResponseMessage.error("获取管理信息失败");
            }

            return ResponseMessage.success("获取管理员信息", userL);
        } catch (Exception e) {
            logUtil.error(UserServiceImpl.class, "获取管理信息失败", e);
            return ResponseMessage.error("获取管理信息失败");
        }
    }

    @Override
    public ResponseMessage<Object> getRolesDelete(Integer id) {
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("用户不存在"));
            System.out.println("user: " + user);
            if (user.getStatus() == 4) {
                userRepository.updateStatusById(id, 5);
            } else if (user.getStatus() == 5) {
                userRepository.updateStatusById(id, 4);
            } else {
                logUtil.error(UserServiceImpl.class, "用户状态不正确");
                return ResponseMessage.error("用户状态不正确");
            }
//            userRepository.updateStatusToZeroById(id);
            return ResponseMessage.success("用户状态更新完毕");
        } catch (Exception e) {
            logUtil.error(UserServiceImpl.class, "删除用户失败", e);
            return ResponseMessage.error("删除用户失败");
        }
    }

    @Override
    public ResponseMessage<Object> getLoginIndex(String phone, String role) {
        try {
            switch (role) {
                case "STUDENT" -> {
                    Object student = studentInfoRepository.findByPhone(phone);
                    return ResponseMessage.success("获取学生信息成功", student);
                } case "TEACHER" -> {
                    TeacherInfo teacherInfo = teacherInfoRepository.findByPhone(phone);
                    return ResponseMessage.success("获取老师信息成功", teacherInfo);
                }
                case "UNIT" -> {
                    UnitUser unitUser = unitUserRepository.findByPhone(phone);
                    return ResponseMessage.success("获取单位信息成功", unitUser);
                }
                default -> {
                    User user = userRepository.findByPhone(phone);
                    return ResponseMessage.success("返回用户信息成功", user);
                }
            }
        } catch (Exception e) {
            logUtil.error(UserServiceImpl.class, "获取用户信息失败", e);
            return ResponseMessage.error("获取用户信息失败");
        }
    }

    @Override
    public ResponseMessage<Object> saveLoginIndexSave(UserIdDto userIdDto) {
        try {
            User user = new User();
            BeanUtils.copyProperties(userIdDto, user);
            userRepository.save(user);
            return ResponseMessage.success("保存用户信息成功");
        } catch (Exception e) {
            logUtil.error(UserServiceImpl.class, "保存用户信息失败", e);
            return ResponseMessage.error("保存用户信息失败");
        }
    }

    @Override
    public ResponseMessage<Object> findNotices(String phone) {
        try {
            StudentInfo student = (StudentInfo) studentInfoRepository.findByPhone(phone);
            if (student == null) {
                return ResponseMessage.error("获取学生信息失败");
            }
            List<String> phones = Arrays.asList(student.getTeacherPhone(), student.getUnitPhone());
//            List<Integer> status = Arrays.asList(0, 3);
            List<Announcement> announcementList = announcementRepository.findByPublisherInAndStatus(phones,2);
            if (announcementList.isEmpty()) {
                return ResponseMessage.error("无公告或获取公告失败");
            }
            return ResponseMessage.success("获取公告成功", announcementList);

        } catch (Exception e) {
            logUtil.error(UserServiceImpl.class, "获取公告失败", e);
            return ResponseMessage.error("获取公告失败");
        }
    }

    @Override
    public ResponseMessage<Object> findNoticesTN(String phone) {
        try {
            List<Announcement> announcementList = announcementRepository.findByPublisherAndStatus(phone, 2);
            if (announcementList.isEmpty()) {
                return ResponseMessage.error("无公告或获取公告失败");
            }
            return ResponseMessage.success("获取公告成功", announcementList);
        } catch (Exception e) {
            logUtil.error(UserServiceImpl.class, "获取公告失败", e);
            return ResponseMessage.error("获取公告失败");
        }
    }

    @Override
    public ResponseMessage<Object> findNoticesA() {
        try {
            List<Announcement> announcementList = announcementRepository.findByStatus(2);
            if (announcementList.isEmpty()) {
                return ResponseMessage.error("无公告或获取公告失败");
            }
            return ResponseMessage.success("获取公告成功", announcementList);
        } catch (Exception e) {
            logUtil.error(UserServiceImpl.class, "获取公告失败", e);
            return ResponseMessage.error("获取公告失败");
        }
    }

    @Override
    public ResponseMessage<Object> findNoticesSys() {
        try {
            List<Announcement> announcementList = announcementRepository.findByStatus(1);
            if (announcementList.isEmpty()) {
                return ResponseMessage.error("无公告或获取公告失败");
            }
            return ResponseMessage.success("获取公告成功", announcementList);
        } catch (Exception e) {
            logUtil.error(UserServiceImpl.class, "获取公告失败", e);
            return ResponseMessage.error("获取公告失败");
        }
    }

}
