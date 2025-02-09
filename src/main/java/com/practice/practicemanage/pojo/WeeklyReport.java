package com.practice.practicemanage.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Entity
@Table(name = "weekly_reports")
public class WeeklyReport {
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

    @Column(name = "teacher_phone", length = 20)
    private String teacherPhone;

    @Column(name = "status", nullable = false)
    private Byte status;

}