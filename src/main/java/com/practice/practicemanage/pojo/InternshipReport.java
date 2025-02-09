package com.practice.practicemanage.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Setter
@Getter
@ToString
@Entity
@Table(name = "internship_reports")
public class InternshipReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "student_phone", length = 20)
    private String studentPhone;

    @Column(name = "title", length = 200)
    private String title;

    @Lob
    @Column(name = "content")
    private String content;

    @Column(name = "score", precision = 5, scale = 2)
    private BigDecimal score;

    @Column(name = "teacher_phone", length = 20)
    private String teacherPhone;

    @Column(name = "status", nullable = false)
    private Byte status;

}