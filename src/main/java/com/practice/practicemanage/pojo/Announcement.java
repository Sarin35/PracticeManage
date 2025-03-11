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
@Table(name = "announcement")
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 100)
    @NotNull
    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @NotNull
    @Lob
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Size(max = 20)
    @NotNull
    @Column(name = "publisher", nullable = false, length = 20)
    private String publisher;

    @Column(name = "status")
    private Integer status;

    @Column(name = "create_time")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @JsonDeserialize(using = InstantMillisDeserializer.class) // 自定义反序列化器
    private Instant createTime;

    @Column(name = "update_time")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @JsonDeserialize(using = InstantMillisDeserializer.class) // 自定义反序列化器
    private Instant updateTime;

}