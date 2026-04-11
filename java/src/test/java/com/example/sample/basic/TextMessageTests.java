package com.example.sample.basic;

import java.text.MessageFormat;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextMessageTests {
    
    private static final Logger log = LoggerFactory.getLogger(TextMessageTests.class);

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
