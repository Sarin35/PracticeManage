package com.practice.practicemanage.pojo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @NotNull(message = "id不能为空")
    private Integer id;

    @Column(name = "username", length = 100)
    @NotNull(message = "账号不能为空")
    private String userName;

    @Column(name = "password", length = 100)
    @NotNull(message = "密码不能为空")
    private String passWord;

    @Column(name = "phone", length = 11)
    @NotNull(message = "手机号不能为空")
    @Pattern(regexp = "^1((3[0-9])|(4[5-9])|(5[0-3,5-9])|(6[5,6])|(7[0-9])|(8[0-9])|(9[1,8,9]))\\d{8}$", message = "手机号格式不正确")
    private String phone;

    @Column(name = "status", nullable = false)
    @NotNull(message = "状态不能为空")
    private Byte status;

}