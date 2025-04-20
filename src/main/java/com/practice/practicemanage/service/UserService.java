package com.practice.practicemanage.service;

import com.practice.practicemanage.pojo.User;
import com.practice.practicemanage.pojo.dto.UserDto;
import com.practice.practicemanage.pojo.dto.UserIdDto;
import com.practice.practicemanage.response.ResponseMessage;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UserService {

    /*
       查询用户 测试
      @return List<User>
     */
//    List<User> userList();

    /**
     *  插入用户 测试
     * @return User
     */
    User add(UserDto user);

    @Cacheable(value = "userList")
    List<User> userList();

    /**
     *  根据id查询用户 测试
     * @return User
     */
    User getById(Integer userId);

    /**
     *  修改用户 测试
     * @return User
     */
    User edit(UserIdDto user);

    /**
     *  删除用户 测试
     * @return User
     */
    Optional<User> updataById(Integer userId);

    /**
     * 根据用户名在redis查询用户
     */
    User getUserByToken(String head, String token);

    /**
     * 根据用户名在redis查询用户
     */
    UserDetails loadUserByUsername(String head, String token, String userName);

    /**
     * 根据用户名和密码查询用户
     */
    User findUser(String userName, String password);

    ResponseMessage<Object> getRoles();

    ResponseMessage<Object> getRolesDelete(Integer id, Integer status);

    ResponseMessage<Object> getLoginIndex(String phone, String role);

    ResponseMessage<Object> saveLoginIndexSave(UserIdDto userIdDto);

    ResponseMessage<Object> findNotices(String phone);

    ResponseMessage<Object> findNoticesTN(String phone);

    ResponseMessage<Object> findNoticesA();

    ResponseMessage<Object> findNoticesSys();

    ResponseMessage<Object> getRolesSA();
}
