package com.practice.practicemanage.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.practice.practicemanage.utils.InstantMillisDeserializer;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * DTO for {@link com.practice.practicemanage.pojo.Assignment}
 */
@Value
public class AssignmentDto implements Serializable {
    @Size(max = 20)
    String teacherPhone;
    @Size(max = 200)
    String title;
    String content;
    BigDecimal score;
    @Size(max = 20)
    String studentPhone;
    @NotNull
    Byte status;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @JsonDeserialize(using = InstantMillisDeserializer.class) // 自定义反序列化器
    private Instant puttimes;
}