package com.practice.practicemanage.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class TypeConversionUtil {

    @Autowired
    private LogUtil logUtil;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    // 将 String 转换为 Integer
    public Integer toInteger(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null; // 返回 null 或可以抛出自定义异常
        }
    }

    // String 转 Byte
    public Byte toByte(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            return toInteger(value).byteValue();
        } catch (NumberFormatException e) {
            return null; // 返回 null 或可以抛出自定义异常
        }
    }

    // 将 String 转换为 Long
    public Long toLong(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return null; // 返回 null 或可以抛出自定义异常
        }
    }

    // 将 String 转换为 Double
    public Double toDouble(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return null; // 返回 null 或可以抛出自定义异常
        }
    }

    // 将 String 转换为 Boolean
    public Boolean toBoolean(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return Boolean.parseBoolean(value);
    }

    // 将 Object 转换为 Integer
    public Integer toInteger(Object value) {
        if (value == null) {
            return null;
        }
        try {
            return Integer.valueOf(value.toString());
        } catch (NumberFormatException e) {
            return null; // 返回 null 或可以抛出自定义异常
        }
    }

    // 将 Object 转换为 Long
    public Long toLong(Object value) {
        if (value == null) {
            return null;
        }
        try {
            return Long.valueOf(value.toString());
        } catch (NumberFormatException e) {
            return null; // 返回 null 或可以抛出自定义异常
        }
    }

    // 将 Object 转换为 Double
    public Double toDouble(Object value) {
        if (value == null) {
            return null;
        }
        try {
            return Double.valueOf(value.toString());
        } catch (NumberFormatException e) {
            return null; // 返回 null 或可以抛出自定义异常
        }
    }

    // 将 Object 转换为 String
    public String toString(Object value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    // 将 Object 转换为 Boolean
    public Boolean toBoolean(Object value) {
        if (value == null) {
            return null;
        }
        return Boolean.parseBoolean(value.toString());
    }

    // 将 String 转换为 Date (假设使用的格式为 yyyy-MM-dd)
    public java.util.Date toDate(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            return new java.text.SimpleDateFormat("yyyy-MM-dd").parse(value);
        } catch (java.text.ParseException e) {
            return null; // 返回 null 或可以抛出自定义异常
        }
    }

    // 将 Date 转换为 String
    public String toString(java.util.Date value) {
        if (value == null) {
            return null;
        }
        return new java.text.SimpleDateFormat("yyyy-MM-dd").format(value);
    }

    public String toJson(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            logUtil.error(TypeConversionUtil.class, "转换为 JSON 字符串失败", e);
            return null; // 返回 null 或可以抛出自定义异常
        }
    }

    /**
     * 将 Object 类型转换为指定类型的 List
     *
     * @param obj Object 类型的输入
     * @param clazz List 中元素的类型
     * @param <T> 目标 List 中元素的类型
     * @return 转换后的 List
     * @throws IOException 如果转换失败
     */
    public <T> List<T> convertObjectToList(Object obj, Class<T> clazz) throws IOException {
        try {
            // 使用 ObjectMapper 将 Object 转换为字符串
            String json = objectMapper.writeValueAsString(obj);
            // 将 JSON 字符串转换为指定类型的 List
            return objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (JsonProcessingException e) {
            logUtil.error(TypeConversionUtil.class, "转换为 list 失败", e);
            return null; // 返回 null 或可以抛出自定义异常
        }
    }

    // 将 Object 转换为指定类型
    public <T> T convertToClass(Object value, Class<T> targetType) {
        if (value == null) {
            return null;
        }
        try {
            // 如果目标类型是字符串类型，直接转换为 String
            if (targetType == String.class) {
                return targetType.cast(value.toString());
            }

            // 如果目标类型是复杂类型（例如 User），尝试将 JSON 字符串转换为该类型
            if (value instanceof String) { // 判断是否为 JSON 字符串
                return objectMapper.readValue((String) value, targetType); // 将 JSON 字符串转换为指定类型
            }
            // 否则通过基本类型转换
            return targetType.cast(value);
        } catch (ClassCastException | JsonProcessingException e) {
            logUtil.error(TypeConversionUtil.class, "类型转换失败", e);
            return null;  // 返回 null 或可以抛出自定义异常
        }
    }

    // 将 String 转换为指定类型
    public <T> T convertStringToClass(String value, Class<T> targetType) {
        if (value == null) {
            return null;
        }
        try {
            // 如果目标类型是复杂类型（例如 User），尝试将 JSON 字符串转换为该类型
            // 判断是否为 JSON 字符串
            return objectMapper.readValue(value, targetType); // 将 JSON 字符串转换为指定类型
            // 否则通过基本类型转换
        } catch (ClassCastException | JsonProcessingException e) {
            logUtil.error(TypeConversionUtil.class, "类型转换失败", e);
            return null;  // 返回 null 或可以抛出自定义异常
        }
    }
}
