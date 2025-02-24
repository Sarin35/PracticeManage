package com.practice.practicemanage.repository;

import com.practice.practicemanage.pojo.Assignment;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {

    List<Assignment> findByTeacherPhoneAndStatus(@Size(max = 20) String teacherPhone, Byte status);

    List<Assignment> findByStudentPhoneAndStatus(@Size(max = 20) String studentPhone, @NotNull Byte status);
}