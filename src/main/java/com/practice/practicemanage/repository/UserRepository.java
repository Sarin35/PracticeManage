package com.practice.practicemanage.repository;

import com.practice.practicemanage.pojo.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Transactional
    @Query("update User u set u.status = 0 where u.id = :id")
    @Modifying
    int updateStatusToZeroById(@NotNull(message = "id不能为空") Integer id);

}