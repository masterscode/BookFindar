package com.findar.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.findar.common.config.JsonDateSerializer;
import lombok.SneakyThrows;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class JsonUtil {
    public static ObjectMapper objectMapper = new ObjectMapper();

    static {
        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDateTime.class, new JsonDateSerializer.LocalDateTimeSerializer());
        module.addDeserializer(LocalDateTime.class, new JsonDateSerializer.LocalDateTimeDeserializer());

        objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES, false)
                .configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE)
                .registerModule(module)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
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
