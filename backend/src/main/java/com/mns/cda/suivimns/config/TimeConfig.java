package com.mns.cda.suivimns.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * Every LocalDateTime in the app is UTC: the JVM runs in UTC (see SuiviMnsApplication.main),
 * and the JSON carries the "Z" suffix so browsers convert to the visitor's time zone on display.
 * Without the suffix, a browser reads "2026-11-10T11:00:00" as its own local time.
 */
@Configuration
public class TimeConfig {

    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer utcLocalDateTime() {
        return builder -> builder
                .serializerByType(LocalDateTime.class, new UtcLocalDateTimeSerializer())
                .deserializerByType(LocalDateTime.class, new UtcLocalDateTimeDeserializer());
    }

    /** Writes "2026-11-10T11:00:00Z". */
    public static class UtcLocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
        @Override
        public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers)
                throws IOException {
            gen.writeString(value.atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        }
    }

    /** Reads a value with an offset ("…Z", "…+01:00") as UTC; a value without one is taken as UTC already. */
    public static class UtcLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
        @Override
        public LocalDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
            String text = parser.getValueAsString();
            if (text == null || text.isBlank()) {
                return null;
            }
            String value = text.trim();
            boolean hasOffset = value.endsWith("Z") || value.matches(".*T.*[+-]\\d{2}:?\\d{2}$");
            return hasOffset
                    ? OffsetDateTime.parse(value).withOffsetSameInstant(ZoneOffset.UTC).toLocalDateTime()
                    : LocalDateTime.parse(value);
        }
    }
}
