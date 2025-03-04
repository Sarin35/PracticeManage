package com.practice.practicemanage.pojo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "student_info")
public class StudentInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 20)
    @Column(name = "phone", length = 20)
    private String phone;

    @Size(max = 100)
    @Column(name = "name", length = 100)
    private String name;

    @Size(max = 50)
    @Column(name = "student_number", length = 50)
    private String studentNumber;

    @Size(max = 200)
    @Column(name = "school", length = 200)
    private String school;

    @Size(max = 20)
    @Column(name = "teacher_phone", length = 20)
    private String teacherPhone;

    @Size(max = 200)
    @Column(name = "unit_name", length = 200)
    private String unitName;

    @Size(max = 20)
    @Column(name = "unit_phone", length = 20)
    private String unitPhone;

    @Size(max = 50)
    @Column(name = "student_batch", length = 50)
    private String studentBatch;

    @Column(name = "status")
    private Byte status;

}