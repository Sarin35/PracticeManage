package com.practice.practicemanage.pojo.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.practice.practicemanage.pojo.User}
 */
@Value
public class UserDto implements Serializable {
    String userName;
    String passWord;
    String phone;
    Byte status;
}