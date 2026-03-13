package com.example.sample.basic;

import java.text.MessageFormat;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TextMessageTests {
    private final String message = """
            {
                "name": "test",
                "id": 7777,
                "group_name_path": "/dev/%TEAM_NAME%/grade",
                "timestamp": "{0}"
            }
            """;

    @Test
    public void textTest() {
        var now = OffsetDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        log.info(MessageFormat.format(message, now));
    }
}
