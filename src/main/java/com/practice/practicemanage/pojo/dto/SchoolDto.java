package com.practice.practicemanage.pojo.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.practice.practicemanage.pojo.School}
 */
@Value
public class SchoolDto implements Serializable {
    String schoolName;
    Byte status;
}