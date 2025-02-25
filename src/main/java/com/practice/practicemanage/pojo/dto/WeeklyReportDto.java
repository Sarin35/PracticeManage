package com.practice.practicemanage.pojo.dto;

import lombok.Value;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link com.practice.practicemanage.pojo.WeeklyReport}
 */
@Value
public class WeeklyReportDto implements Serializable {
    Integer id;
    String studentPhone;
    String title;
    String content;
    String teacherPhone;
    Byte status;
    Instant puttime;
}