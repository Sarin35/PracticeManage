package com.practice.practicemanage.pojo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * DTO for {@link com.practice.practicemanage.pojo.Assignment}
 */
@Value
public class AssignmentIdDto implements Serializable {
    Integer id;
    @Size(max = 20)
    String teacherPhone;
    @Size(max = 200)
    String title;
    String content;
    BigDecimal score;
    @Size(max = 20)
    String studentPhone;
    @NotNull
    Byte status;
    Instant puttimes;
}