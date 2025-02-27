package com.practice.practicemanage.pojo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "internship_grades")
public class InternshipGrade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 20)
    @Column(name = "student_phone", length = 20)
    private String studentPhone;

    @Column(name = "grade", precision = 5, scale = 2)
    private String grade;

    @Size(max = 20)
    @Column(name = "teacher_phone", length = 20)
    private String teacherPhone;

    @NotNull
    @Column(name = "status", nullable = false)
    private Byte status;

}