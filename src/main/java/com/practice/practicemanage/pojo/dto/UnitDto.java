package com.practice.practicemanage.pojo.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.practice.practicemanage.pojo.Unit}
 */
@Value
public class UnitDto implements Serializable {
    String unitName;
    Byte status;
}