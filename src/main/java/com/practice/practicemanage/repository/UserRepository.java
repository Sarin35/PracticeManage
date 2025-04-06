package com.practice.practicemanage.repository;

import com.practice.practicemanage.pojo.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {


    /**
     * 根据用户名查询用户
     */
//    这两个注解是为了让这个方法支持事务，用于修改数据
    @Transactional
    @Modifying
    @Query("update User u set u.status = :status where u.id = :id")
    void updateStatusById(@NotNull(message = "id不能为空") Integer id, @NotNull(message = "status不能为空") Integer status);

    @Transactional
    @Query("update User u set u.status = 0 where u.id = :id")
    @Modifying
    void updateStatusToZeroById(@NotNull(message = "id不能为空") Integer id);

    User findByUserName(String userName);

    User findByUserNameAndPassWord(String userName, String password);

    List<User> findByStatus(@NotNull(message = "状态不能为空") Byte status);
    List<User> findByStatusIn(Collection<Byte> statuses);


    User findByPhone(@Pattern(regexp = "^1((3[0-9])|(4[5-9])|(5[0-3,5-9])|(6[5,6])|(7[0-9])|(8[0-9])|(9[1,8,9]))\\d{8}$", message = "手机号格式不正确") String phone);

    boolean existsByUserNameOrPhone(@NotNull(message = "账号不能为空") String userName, @Pattern(regexp = "^1((3[0-9])|(4[5-9])|(5[0-3,5-9])|(6[5,6])|(7[0-9])|(8[0-9])|(9[1,8,9]))\\d{8}$", message = "手机号格式不正确") String phone);
}