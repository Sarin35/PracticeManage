package com.practice.practicemanage.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Setter
@Getter
@ToString
@Entity
@Table(name = "internship_guidance")
public class InternshipGuidance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "guidance_time")
    private Instant guidanceTime;

    @Lob
    @Column(name = "guidance_content")
    private String guidanceContent;

    @Column(name = "student_phone", length = 20)
    private String studentPhone;

    @Column(name = "unit_name", length = 200)
    private String unitName;

    @Column(name = "school_name", length = 200)
    private String schoolName;

    @Column(name = "teacher_phone", length = 20)
    private String teacherPhone;

    @Column(name = "status", nullable = false)
    private Byte status;

}