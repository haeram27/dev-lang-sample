package com.example.sample.exam;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import com.example.sample.EvaluatedTimeTests;

public class TimeDateTests extends EvaluatedTimeTests {

    /*
     * ZoneId : Sting expression of timezone
     */
    @Test
    public void zoneIdTest() {
        System.out.println(ZoneId.systemDefault()); // Asia/Seoul
        System.out.println(ZoneId.of("Asia/Seoul")); // Asia/Seoul
        System.out.println(ZoneId.of("UTC")); // UTC
        System.out.println(ZoneId.ofOffset("UTC", ZoneOffset.ofHours(9))); // UTC+09:00
        System.out.println(ZoneId.ofOffset("", ZoneOffset.ofHours(9))); // +09:00
        // System.out.println(ZoneId.getAvailableZoneIds());
    }

    @Test
    public void localDateTimeTest() {
        /*
         * time to long
         */
        long utcTimestamp = LocalDateTime.now().atZone(ZoneId.of("UTC")).toInstant().toEpochMilli();
        // 2024-03-10T01:21:36.022Z
        System.out.println(LocalDateTime.ofInstant(Instant.ofEpochMilli(utcTimestamp), ZoneId.of("UTC")));
        // 2024-03-10T01:21:36.022Z[UTC]
        System.out.println(ZonedDateTime.ofInstant(Instant.ofEpochMilli(utcTimestamp), ZoneId.of("UTC")));

        long localTimestamp = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        // 2024-03-10T01:21:36.023+09:00[Asia/Seoul]
        System.out.println(LocalDateTime.ofInstant(Instant.ofEpochMilli(localTimestamp), ZoneId.systemDefault()));
        // 2024-03-10T01:21:36.023+09:00[Asia/Seoul]
        System.out.println(ZonedDateTime.ofInstant(Instant.ofEpochMilli(localTimestamp), ZoneId.systemDefault()));
    }

    // OffsetDateTime = LocalDateTime + ZoneOffset
    @Test
    public void offsetDateTimeTest() {
        String date = "2024-05-13";
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("uuuu-MM-dd");
        OffsetDateTime offsetTime = OffsetDateTime.of(LocalDate.parse(date, dateFormat), LocalTime.of(20, 30, 40),
                ZoneOffset.ofHoursMinutes(0, 0));
        System.out.println(offsetTime);
    }

    // OffsetDateTime = LocalDateTime + ZoneOffset + ZoneId
    @Test
    public void zonedDateTimeTest() {
        String date = "2024-05-13";
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("uuuu-MM-dd");
        ZonedDateTime zoneTime = ZonedDateTime.of(LocalDate.parse(date, dateFormat), LocalTime.of(21, 31, 41),
                ZoneId.of("UTC"));
        System.out.println(zoneTime);
    }

}
