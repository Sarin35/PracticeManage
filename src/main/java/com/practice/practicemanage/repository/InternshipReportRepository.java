package com.practice.practicemanage.repository;

import com.practice.practicemanage.pojo.InternshipReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InternshipReportRepository extends JpaRepository<InternshipReport, Integer> {
}