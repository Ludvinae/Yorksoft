package com.mns.cda.suivimns.unit.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mns.cda.suivimns.config.TimeConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TimeConfigUnitTest {

    record Payload(LocalDateTime at, LocalDate day) {}

    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        // Same default as Spring Boot's auto-configured mapper: ISO strings, not arrays
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder()
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        new TimeConfig().utcLocalDateTime().customize(builder);
        mapper = builder.build();
    }

    @Test
    void writesLocalDateTimeAsUtcWithZSuffix() throws Exception {
        String json = mapper.writeValueAsString(
                new Payload(LocalDateTime.of(2026, 11, 10, 11, 0, 0), LocalDate.of(2026, 11, 10)));

        assertEquals("{\"at\":\"2026-11-10T11:00:00Z\",\"day\":\"2026-11-10\"}", json);
    }

    @Test
    void keepsSecondFractions() throws Exception {
        String json = mapper.writeValueAsString(new Payload(LocalDateTime.of(2026, 11, 10, 11, 0, 0, 123_000_000), null));

        assertEquals("{\"at\":\"2026-11-10T11:00:00.123Z\",\"day\":null}", json);
    }

    @Test
    void readsUtcValue() throws Exception {
        Payload payload = mapper.readValue("{\"at\":\"2026-11-10T11:00:00Z\"}", Payload.class);

        assertEquals(LocalDateTime.of(2026, 11, 10, 11, 0), payload.at());
    }

    @Test
    void convertsAnOffsetValueToUtc() throws Exception {
        Payload payload = mapper.readValue("{\"at\":\"2026-11-10T12:00:00+01:00\"}", Payload.class);

        assertEquals(LocalDateTime.of(2026, 11, 10, 11, 0), payload.at());
    }

    @Test
    void takesAValueWithoutOffsetAsUtc() throws Exception {
        Payload payload = mapper.readValue("{\"at\":\"2026-11-10T11:00:00\"}", Payload.class);

        assertEquals(LocalDateTime.of(2026, 11, 10, 11, 0), payload.at());
    }

    @Test
    void readsNullAndBlankAsNull() throws Exception {
        assertNull(mapper.readValue("{\"at\":null}", Payload.class).at());
        assertNull(mapper.readValue("{\"at\":\"\"}", Payload.class).at());
    }
}
