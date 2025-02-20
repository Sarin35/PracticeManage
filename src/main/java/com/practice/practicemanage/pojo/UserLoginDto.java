package com.practice.practicemanage.pojo;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link User}
 */
@Value
public class UserLoginDto implements Serializable {
    @NotNull(message = "账号不能为空")
    String userName;
    @NotNull(message = "密码不能为空")
    String passWord;
}