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

import java.util.Collection;

@Repository
public interface WeeklyReportRepository extends JpaRepository<WeeklyReport, Integer> {
    
    Page<WeeklyReport> findByStudentPhoneAndTeacherPhoneAndStatusNot(String studentPhone, String teacherPhone, int status, Pageable pageable);

    Page<WeeklyReport> findByStudentPhoneAndTeacherPhoneAndStatus(String studentPhone, String teacherPhone, int status, Pageable pageable);
    
    Page<WeeklyReport> findByTeacherPhoneAndStatus(String teacherPhone, Byte status, Pageable pageable);

    Page<WeeklyReport> findByTeacherPhoneAndStatusNot(String teacherPhone, Byte status, Pageable pageable);


    @Modifying
    @Transactional
    @Query("UPDATE WeeklyReport a SET a.status = :status WHERE a.id = :id")
    int updateStatusById(@Param("id") Integer id, @Param("status") Byte status);

//    @Query("SELECT y FROM WeeklyReport y WHERE " +
//            "(y.studentPhone = :studentPhone OR :studentPhone IS NULL) AND " +
//            "(y.teacherPhone = :teacherPhone OR :teacherPhone IS NULL) AND " +
//            "(y.title LIKE %:title% OR :title IS NULL) AND " +
//            "y.status != 0")
//    Page<WeeklyReport> searchByPhoneAndTitle(@Param("studentPhone") String studentPhone,
//                                             @Param("teacherPhone") String teacherPhone,
//                                             @Param("title") String title,
//                                             Pageable pageable);
//
//    @Query("SELECT y FROM WeeklyReport y WHERE " +
//            "(y.teacherPhone = :teacherPhone OR :teacherPhone IS NULL) AND " +
//            "(y.title LIKE %:title% OR :title IS NULL) AND " +
//            "y.status != 0")
//    Page<WeeklyReport> searchByPhoneAndTitles(@Param("teacherPhone") String teacherPhone,
//                                             @Param("title") String title,
//                                             Pageable pageable);

//    Page<WeeklyReport> findByTeacherPhoneAndStudentPhoneIn(String teacherPhone, Collection<String> studentPhones, Pageable pageable);

//    Page<WeeklyReport> findByTeacherPhoneAndStudentPhoneInAndStatusNot(String teacherPhone, Collection<String> studentPhones, Byte status);

    Page<WeeklyReport> findByTeacherPhoneAndStatusNotAndStudentPhoneIn(String teacherPhone, Byte status, Collection<String> studentPhones, Pageable pageable);

    Page<WeeklyReport> findByTeacherPhoneAndTitleContainingAndStatus(String teacherPhone, String title, Byte status, Pageable pageable);

    Page<WeeklyReport> findByTeacherPhoneAndTitleContainingAndStatusNot(String teacherPhone, String title, Byte status, Pageable pageable);

    Page<WeeklyReport> findByTeacherPhoneAndStatusAndStudentPhoneIn(String teacherPhone, Byte status, Collection<String> studentPhones, Pageable pageable);

    Page<WeeklyReport> findByTeacherPhoneAndTitleContainingAndStudentPhoneInAndStatus(String teacherPhone, String title, Collection<String> studentPhones, Byte status, Pageable pageable);

    Page<WeeklyReport> findByTeacherPhoneAndTitleContainingAndStudentPhoneInAndStatusNot(String teacherPhone, String title, Collection<String> studentPhones, Byte status, Pageable pageable);

    Page<WeeklyReport> findByStudentPhoneAndTeacherPhoneAndTitleContainingAndStatus(String studentPhone, String teacherPhone, String title, Byte status, Pageable pageable);

    Page<WeeklyReport> findByStudentPhoneAndTeacherPhoneAndTitleContainingAndStatusNot(String studentPhone, String teacherPhone, String title, Byte status, Pageable pageable);
}