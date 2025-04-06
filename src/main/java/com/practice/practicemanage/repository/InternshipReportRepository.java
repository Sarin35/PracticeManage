package com.practice.practicemanage.repository;

import com.practice.practicemanage.pojo.InternshipReport;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InternshipReportRepository extends JpaRepository<InternshipReport, Integer> {

    Page<InternshipReport> findByStudentPhoneAndStatusNot(@Size(max = 20) String studentPhone, @NotNull Byte status, Pageable pageable);

    Page<InternshipReport> findByTeacherPhoneAndStatus(@Size(max = 20) String teacherPhone, @NotNull Byte status, Pageable pageable);

    Page<InternshipReport> findByTeacherPhoneAndStatusNot(@Size(max = 20) String teacherPhone, @NotNull Byte status, Pageable pageable);
}