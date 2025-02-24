package com.practice.practicemanage.repository;

import com.practice.practicemanage.pojo.StudentInfo;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentInfoRepository extends JpaRepository<StudentInfo, Integer> {

    Object findByPhone(String phone);
}