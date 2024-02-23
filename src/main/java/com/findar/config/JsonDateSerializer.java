package com.findar.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonDateSerializer {

    public static class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

        private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        @Override
        public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            return LocalDateTime.parse(p.getValueAsString(), formatter);
        }
    }


    public static class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

        private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        @Override
        public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString(formatter.format(value));
        }
    }
}
