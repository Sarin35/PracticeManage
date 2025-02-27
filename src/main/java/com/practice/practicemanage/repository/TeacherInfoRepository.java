package com.practice.practicemanage.repository;

import com.practice.practicemanage.pojo.TeacherInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherInfoRepository extends JpaRepository<TeacherInfo, Integer> {

    TeacherInfo findByPhone(String phone);


}