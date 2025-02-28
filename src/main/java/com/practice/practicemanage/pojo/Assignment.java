package com.practice.practicemanage.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.practice.practicemanage.utils.InstantMillisDeserializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "assignments")
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 20)
    @Column(name = "teacher_phone", length = 20)
    private String teacherPhone;

    @Size(max = 200)
    @Column(name = "title", length = 200)
    private String title;

    @Lob
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "score", precision = 5, scale = 2)
    private BigDecimal score;

    @Size(max = 20)
    @Column(name = "student_phone", length = 20)
    private String studentPhone;

    @NotNull
    @Column(name = "status", nullable = false)
    private Byte status;

    @Column(name = "puttimes")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @JsonDeserialize(using = InstantMillisDeserializer.class) // 自定义反序列化器
    private Instant puttimes;

    @Transient
    private String teacger;

    @Transient
    private String studentNumber;

    @Transient
    private String name;

}