package com.practice.practicemanage.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Instant;

public class InstantMillisDeserializer extends JsonDeserializer<Instant> {

    @Override
    public Instant deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        // 获取原始时间戳（单位是毫秒）
        long timestamp = p.getLongValue();
        // 将时间戳转换为 Instant
        return Instant.ofEpochMilli(timestamp);
    }
}
