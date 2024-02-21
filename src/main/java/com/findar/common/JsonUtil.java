package com.findar.common;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

public class JsonUtil {
    static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @SneakyThrows
    public static String toJson(Object src) {
        return objectMapper.writeValueAsString(src);
    }

    public static <T> T fromJson(final String o, Class<T> type) {
        try {
            return objectMapper.readValue(o, type);
        } catch (Exception e) {
            throw new RuntimeException("Error while parsing json " + e.getMessage());
        }
    }
}
