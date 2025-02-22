package com.practice.practicemanage.utils;


import org.springframework.util.DigestUtils;

public class PasswordUtil {

    // 判断是否是已经MD5加密的密码
    public static boolean isMd5Encrypted(String password) {
        // 判断密码长度是否为32，并且是否是16进制字符
        return password != null && password.length() == 32 && password.matches("[a-fA-F0-9]{32}");
    }

    // 比较密码的方法
    public static String comparePassword(String inputPassword) {
        // 如果已经加密过，则直接返回
        if (isMd5Encrypted(inputPassword)) {
            return inputPassword;
        } else {
            // 如果输入的密码未加密，则加密后再返回
            return DigestUtils.md5DigestAsHex(inputPassword.getBytes());
        }
    }
}
