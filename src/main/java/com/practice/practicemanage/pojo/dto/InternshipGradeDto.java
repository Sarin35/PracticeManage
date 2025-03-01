package com.practice.practicemanage.pojo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.practice.practicemanage.pojo.InternshipGrade}
 */
@Value
public class InternshipGradeDto implements Serializable {
    Integer id;
    @Size(max = 20)
    String studentPhone;
    String grade;
    @Size(max = 20)
    String teacherPhone;
    @NotNull
    Byte status;
}