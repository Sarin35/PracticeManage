package com.practice.practicemanage.pojo.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link com.practice.practicemanage.pojo.User}
 */
public class UserDto implements Serializable {
    private final Integer id;
    private final String userName;
    private final String passWord;
    private final String phone;
    private final Byte status;

    public UserDto(Integer id, String userName, String passWord, String phone, Byte status) {
        this.id = id;
        this.userName = userName;
        this.passWord = passWord;
        this.phone = phone;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public String getPhone() {
        return phone;
    }

    public Byte getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto entity = (UserDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.userName, entity.userName) &&
                Objects.equals(this.passWord, entity.passWord) &&
                Objects.equals(this.phone, entity.phone) &&
                Objects.equals(this.status, entity.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, passWord, phone, status);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "userName = " + userName + ", " +
                "passWord = " + passWord + ", " +
                "phone = " + phone + ", " +
                "status = " + status + ")";
    }
}