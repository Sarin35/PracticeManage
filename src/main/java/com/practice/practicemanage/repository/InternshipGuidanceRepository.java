package com.practice.practicemanage.repository;

import com.practice.practicemanage.pojo.InternshipGuidance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InternshipGuidanceRepository extends JpaRepository<InternshipGuidance, Integer> {
}