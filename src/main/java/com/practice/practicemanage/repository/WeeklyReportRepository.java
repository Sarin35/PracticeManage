package com.practice.practicemanage.repository;

import com.practice.practicemanage.pojo.WeeklyReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface WeeklyReportRepository extends JpaRepository<WeeklyReport, Integer> {
    
    Page<WeeklyReport> findByStudentPhoneAndTeacherPhoneAndStatusNot(String studentPhone, String teacherPhone, int status, Pageable pageable);

    Page<WeeklyReport> findByStudentPhoneAndTeacherPhoneAndStatus(String studentPhone, String teacherPhone, int status, Pageable pageable);


    @Modifying
    @Transactional
    @Query("UPDATE WeeklyReport a SET a.status = :status WHERE a.id = :id")
    int updateStatusById(@Param("id") Integer id, @Param("status") Byte status);

    @Query("SELECT y FROM WeeklyReport y WHERE " +
            "(y.studentPhone = :studentPhone OR :studentPhone IS NULL) AND " +
            "(y.teacherPhone = :teacherPhone OR :teacherPhone IS NULL) AND " +
            "(y.title LIKE %:title% OR :title IS NULL) AND " +
            "y.status != 0")
    Page<WeeklyReport> searchByPhoneAndTitle(@Param("studentPhone") String studentPhone,
                                             @Param("teacherPhone") String teacherPhone,
                                             @Param("title") String title,
                                             Pageable pageable);



}