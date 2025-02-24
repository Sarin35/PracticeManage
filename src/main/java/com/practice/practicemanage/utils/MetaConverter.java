package com.practice.practicemanage.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.practicemanage.pojo.Meta;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Converter
@Component
public class MetaConverter implements AttributeConverter<Meta, String> {

    @Autowired
    private TypeConversionUtil typeConversionUtil;

    @Override
    public String convertToDatabaseColumn(Meta meta) {
        return typeConversionUtil.toJson(meta);  // 将 Meta 对象转换为 JSON 字符串
    }

    @Override
    public Meta convertToEntityAttribute(String dbData) {
        return typeConversionUtil.convertStringToClass(dbData, Meta.class);  // 将 JSON 字符串转换为 Meta 对象
    }
}
