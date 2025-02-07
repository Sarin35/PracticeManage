package com.practice.practicemanage.repository;

import com.practice.practicemanage.pojo.WeeklyReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeeklyReportRepository extends JpaRepository<WeeklyReport, Integer> {
}