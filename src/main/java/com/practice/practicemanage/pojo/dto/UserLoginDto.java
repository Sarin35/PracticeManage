package com.practice.practicemanage.pojo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.practice.practicemanage.pojo.User}
 */
@Value
public class UserLoginDto implements Serializable {
    @NotNull(message = "账号不能为空")
    String userName;
    @NotNull(message = "密码不能为空")
    String passWord;
}