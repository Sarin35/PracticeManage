package com.practice.practicemanage.repository;

import com.practice.practicemanage.pojo.TeacherInfo;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TeacherInfoRepository extends JpaRepository<TeacherInfo, Integer> {

    TeacherInfo findByPhone(String phone);

//    @Transactional
//    @Query("update TeacherInfo u set u.status = 0 where u.id = :id")
//    @Modifying
//    void updateStatusToZeroById(@NotNull(message = "id不能为空") Integer id);
//
//    List<TeacherInfo> findBySchool(String school);
    
    long countBySchool(String school);

    List<TeacherInfo> findByStatus(Byte status);

    List<TeacherInfo> findBySchool(String school);
}