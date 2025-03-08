package com.practice.practicemanage.pojo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.practice.practicemanage.pojo.User}
 */
@Value
public class UserRegisterDto implements Serializable {
    @NotNull(message = "账号不能为空")
    String userName;
    @NotNull(message = "密码不能为空")
    String passWord;
    @Pattern(message = "手机号格式不正确", regexp = "^1((3[0-9])|(4[5-9])|(5[0-3,5-9])|(6[5,6])|(7[0-9])|(8[0-9])|(9[1,8,9]))\\d{8}$")
    String phone;
    @NotNull(message = "状态不能为空")
    Byte status;
    @NotNull
    private String school;
}