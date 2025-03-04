package com.practice.practicemanage.pojo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link com.practice.practicemanage.pojo.InternshipGuidance}
 */
@Value
public class InternshipGuidanceIdDto implements Serializable {
    Integer id;
    @Size(max = 255)
    String guidanceTitle;
    String guidanceContent;
    Instant guidanceTime;
    @Size(max = 20)
    String studentPhone;
    @Size(max = 200)
    String unitName;
    @Size(max = 200)
    String schoolName;
    @Size(max = 20)
    String unitPhone;
    @Size(max = 20)
    String teacherPhone;
    @NotNull
    Byte status;
}