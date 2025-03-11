package com.practice.practicemanage.pojo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.practice.practicemanage.pojo.UnitUser}
 */
@Value
@Data
@NoArgsConstructor(force = true)
public class UnitUserDto implements Serializable {
    Integer id;
    String phone;
    String name;
    String unitName;
    Byte status;
}