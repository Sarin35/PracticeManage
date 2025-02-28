package com.practice.practicemanage.repository;

import com.practice.practicemanage.pojo.Assignment;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {

    List<Assignment> findByTeacherPhoneAndStatus(@Size(max = 20) String teacherPhone, Byte status);

//    List<Assignment> findByTeacherPhoneAndStatus(@Size(max = 20) String teacherPhone, List<Byte> status);

    List<Assignment> findByStudentPhoneAndStatus(@Size(max = 20) String studentPhone, @NotNull Byte status);

    @Transactional
    @Modifying
    @Query("UPDATE Assignment a SET a.status = :status WHERE a.id = :id")
    void updateStatusById(@Param("id") Integer id, @Param("status") Byte status);

    List<Assignment> findByTitle(@Size(max = 200) String title);

    List<Assignment> findByTitleAndStudentPhoneNotNull(@Size(max = 200) String title);

    List<Assignment> findByTitleAndStudentPhoneNotNullAndStatus(@Size(max = 200) String title, @NotNull Byte status);

    Page<Assignment> findByTeacherPhoneAndStatus(@Size(max = 20) String teacherPhone, @NotNull Byte status, Pageable pageable);
}