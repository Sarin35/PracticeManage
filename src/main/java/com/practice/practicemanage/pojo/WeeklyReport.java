package com.practice.practicemanage.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.practice.practicemanage.utils.InstantMillisDeserializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Getter
@Setter
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
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "teacher_phone", length = 20)
    private String teacherPhone;

    @Column(name = "status", nullable = false)
    private Byte status;

    @Column(name = "puttime")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @JsonDeserialize(using = InstantMillisDeserializer.class) // 自定义反序列化器
    private Instant puttime;

}