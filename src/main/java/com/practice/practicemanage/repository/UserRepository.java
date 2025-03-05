package com.practice.practicemanage.repository;

import com.practice.practicemanage.pojo.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {


    /**
     * 根据用户名查询用户
     */
//    这两个注解是为了让这个方法支持事务，用于修改数据
    @Transactional
    @Query("update User u set u.status = 0 where u.id = :id")
    @Modifying
    void updateStatusToZeroById(@NotNull(message = "id不能为空") Integer id);

    User findByUserName(String userName);

    User findByUserNameAndPassWord(String userName, String password);

    List<User> findByStatus(@NotNull(message = "状态不能为空") Byte status);


}