package com.practice.practicemanage.repository;

import com.practice.practicemanage.pojo.StudentInfo;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface StudentInfoRepository extends JpaRepository<StudentInfo, Integer> {

    Object findByPhone(String phone);

//    @Transactional
//    @Query("update StudentInfo u set u.status = 0 where u.id = :id")
//    @Modifying
//    void updateStatusToZeroById(@NotNull(message = "id不能为空") Integer id);

    List<StudentInfo> findByTeacherPhone(String teacherPhone);

    List<StudentInfo> findByUnitName(String unitName);

    List<StudentInfo> findByUnitPhone(@Size(max = 20) String unitPhone);

//    List<StudentInfo> findBySchool(@Size(max = 200) String school);

    long countBySchool(@Size(max = 200) String school);

    long countByUnitName(@Size(max = 200) String unitName);

//    List<StudentInfo> findByStatus(Byte status);

    Page<StudentInfo> findByStatus(Byte status, Pageable pageable);

    List<StudentInfo> findByUnitNameAndStatus(@Size(max = 200) String unitName, Byte status);

    List<StudentInfo> findBySchoolAndStatus(@Size(max = 200) String school, Byte status);
}