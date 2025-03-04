package com.practice.practicemanage.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.practice.practicemanage.utils.InstantMillisDeserializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "internship_guidance")
public class InternshipGuidance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "guidance_title")
    private String guidanceTitle;

    @Lob
    @Column(name = "guidance_content", columnDefinition = "TEXT")
    private String guidanceContent;

    @Column(name = "guidance_time")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @JsonDeserialize(using = InstantMillisDeserializer.class) // 自定义反序列化器
    private Instant guidanceTime;

    @Size(max = 20)
    @Column(name = "student_phone", length = 20)
    private String studentPhone;

    @Size(max = 200)
    @Column(name = "unit_name", length = 200)
    private String unitName;

    @Size(max = 200)
    @Column(name = "school_name", length = 200)
    private String schoolName;

    @Size(max = 20)
    @Column(name = "unit_phone", length = 20)
    private String unitPhone;

    @Size(max = 20)
    @Column(name = "teacher_phone", length = 20)
    private String teacherPhone;

    @NotNull
    @Column(name = "status", nullable = false)
    private Byte status;

}