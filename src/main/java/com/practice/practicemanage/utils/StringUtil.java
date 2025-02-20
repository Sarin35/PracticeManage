package com.practice.practicemanage.utils;

public class StringUtil {

    // 判断字符串是否为空 (null 或 "" 或 " " 都视为空)
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    // 判断字符串是否不为空
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    // 判断字符串是否为 null 或空白
    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    // 判断字符串是否不为 null 且不为空白
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    // 参数验证，抛出异常
    public static void validateNotNull(String str, String errorMessage) {
        if (str == null) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    // 参数验证，抛出异常，如果为空或为空白
    public static void validateNotEmpty(String str, String errorMessage) {
        if (isEmpty(str)) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    // 返回字符串的非空版本，如果为空则返回默认值
    public static String defaultIfEmpty(String str, String defaultValue) {
        return isEmpty(str) ? defaultValue : str;
    }

    // 修剪空格并返回字符串
    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    // 如果字符串为空则返回默认值
    public static String defaultIfBlank(String str, String defaultValue) {
        return isBlank(str) ? defaultValue : str;
    }
}
