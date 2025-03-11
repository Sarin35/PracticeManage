package com.practice.practicemanage.pojo.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.practice.practicemanage.pojo.StudentInfo}
 */
@Value
@Data
@NoArgsConstructor(force = true)
public class StudentInfoDto implements Serializable {
    Integer id;
    @Size(max = 20)
    String phone;
    @Size(max = 100)
    String name;
    @Size(max = 50)
    String studentNumber;
    @Size(max = 200)
    String school;
    @Size(max = 20)
    String teacherPhone;
    @Size(max = 200)
    String unitName;
    @Size(max = 20)
    String unitPhone;
    @Size(max = 50)
    String studentBatch;
    Byte status;
}