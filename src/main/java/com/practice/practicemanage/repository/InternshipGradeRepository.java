package com.practice.practicemanage.repository;

import com.practice.practicemanage.pojo.InternshipGrade;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InternshipGradeRepository extends JpaRepository<InternshipGrade, Integer> {
    Object findByStudentPhone(@Size(max = 20) String studentPhone);
}