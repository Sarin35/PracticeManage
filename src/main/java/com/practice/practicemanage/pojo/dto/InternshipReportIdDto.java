package com.practice.practicemanage.pojo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * DTO for {@link com.practice.practicemanage.pojo.InternshipReport}
 */
@Value
public class InternshipReportIdDto implements Serializable {
    Integer id;
    @Size(max = 20)
    String studentPhone;
    @Size(max = 200)
    String title;
    String content;
    BigDecimal score;
    @Size(max = 20)
    String teacherPhone;
    @NotNull
    Byte status;
    Instant puttime;
    String summary;
}