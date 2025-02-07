package com.practice.practicemanage.repository;

import com.practice.practicemanage.pojo.InternshipGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InternshipGradeRepository extends JpaRepository<InternshipGrade, Integer> {
}