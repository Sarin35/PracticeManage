package com.practice.practicemanage.pojo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link com.practice.practicemanage.pojo.Announcement}
 */
@Value
public class AnnouncementDto implements Serializable {
    Long id;
    @NotNull
    @Size(max = 100)
    String title;
    @NotNull
    String content;
    @NotNull
    @Size(max = 20)
    String publisher;
    Integer status;
    Instant createTime;
    Instant updateTime;
}