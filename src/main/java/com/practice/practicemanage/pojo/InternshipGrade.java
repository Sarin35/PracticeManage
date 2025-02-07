package com.practice.practicemanage.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Entity
@Table(name = "internship_grades")
public class InternshipGrade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "student_phone", length = 20)
    private String studentPhone;

    @Column(name = "grade", precision = 5, scale = 2)
    private BigDecimal grade;

    @Column(name = "teacher_phone", length = 20)
    private String teacherPhone;

    @Column(name = "status", nullable = false)
    private Byte status;

}