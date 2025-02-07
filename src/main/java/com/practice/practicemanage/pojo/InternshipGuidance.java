package com.practice.practicemanage.pojo;

import jakarta.persistence.*;

import java.time.Instant;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Instant getGuidanceTime() {
        return guidanceTime;
    }

    public void setGuidanceTime(Instant guidanceTime) {
        this.guidanceTime = guidanceTime;
    }

    public String getGuidanceContent() {
        return guidanceContent;
    }

    public void setGuidanceContent(String guidanceContent) {
        this.guidanceContent = guidanceContent;
    }

    public String getStudentPhone() {
        return studentPhone;
    }

    public void setStudentPhone(String studentPhone) {
        this.studentPhone = studentPhone;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getTeacherPhone() {
        return teacherPhone;
    }

    public void setTeacherPhone(String teacherPhone) {
        this.teacherPhone = teacherPhone;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

}