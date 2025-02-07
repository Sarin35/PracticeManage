package com.practice.practicemanage.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "student_info")
public class StudentInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "student_number", length = 50)
    private String studentNumber;

    @Column(name = "school", length = 200)
    private String school;

    @Column(name = "teacher_phone", length = 20)
    private String teacherPhone;

    @Column(name = "unit_name", length = 200)
    private String unitName;

    @Column(name = "student_batch", length = 50)
    private String studentBatch;

    @Column(name = "status", nullable = false)
    private Byte status;

}