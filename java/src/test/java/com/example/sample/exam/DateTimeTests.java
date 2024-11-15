package com.example.sample.exam;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.junit.jupiter.api.Test;
import com.example.sample.EvaluatedTimeTests;

public class DateTimeTests extends EvaluatedTimeTests {

    /*
     * # DateTimeFormatter (ISO8601 Date/Time foramt)
     * uuuu-MM-ddTHH:mm:ss.SSSSSSSSS<ZoneOffset>[<ZoneId>]
     * 2024-07-31T13:51:33.110172867+09:00[Asia/Seoul]
     * 
     * DateTimeFormatter.ISO_LOCAL_DATE_TIME;  // '2011-12-03T10:15:30'
     * DateTimeFormatter.ISO_OFFSET_DATE_TIME; // '2011-12-03T10:15:30+01:00'
     * DateTimeFormatter.ISO_ZONED_DATE_TIME;  // '2011-12-03T10:15:30+01:00[Europe/Paris]'
     */

    @Test
    public void nowTest() {
        // !!! All of now() return same time value(instant)
        Instant instantNowUTC = Instant.now(); // == ZonedDateTime.now(ZoneOffset.UTC).toInstant();
        LocalDateTime localDateTimeNow = LocalDateTime.now();
        ZonedDateTime zonedDateTimeNowSystemDefaultTimeZone = ZonedDateTime.now(); // system default timezone
        ZonedDateTime zonedDateTimeNowUTCZoneOffset = ZonedDateTime.now(ZoneOffset.UTC);
        ZonedDateTime zonedDateTimeNowUTCZoneId = ZonedDateTime.now(ZoneId.of("UTC"));

        System.out.println(localDateTimeNow); // 2024-07-31T14:53:12.727779080
        System.out.println(zonedDateTimeNowSystemDefaultTimeZone); // 2024-07-31T14:53:12.727779080+09:00[Asia/Seoul]

        System.out.println(instantNowUTC); // 2024-07-31T05:53:12.727779080Z
        System.out.println(zonedDateTimeNowUTCZoneOffset); // 2024-07-31T05:53:12.727779080Z
        System.out.println(zonedDateTimeNowUTCZoneId); // 2024-07-31T05:53:12.727779080Z[UTC]

        System.out.println(zonedDateTimeNowUTCZoneOffset.toLocalDateTime()); // no time transform, just diposal timezone info
        System.out.println(zonedDateTimeNowSystemDefaultTimeZone.toLocalDateTime()); // no time transform, just diposal timezone info

        // below three calls returns same Instant from system clock
        // time values are same but expression of time is changed with given ZoneId
        System.out.println(ZonedDateTime.now()); // same as ZoneId.systemDefalut()
        System.out.println(ZonedDateTime.now(ZoneId.systemDefault()));
        System.out.println(ZonedDateTime.now(ZoneOffset.UTC));

        // below four calls returns same Instant from system clock
        System.out.println(LocalDateTime.now().toInstant(ZoneId.systemDefault().getRules().getOffset(Instant.now()))); // same as ZoneId.systemDefalut()
        System.out.println(ZonedDateTime.now().toInstant());
        System.out.println(ZonedDateTime.now(ZoneId.systemDefault()).toInstant());
        System.out.println(ZonedDateTime.now(ZoneOffset.UTC).toInstant());
    }

    /*
     * ZoneId : Sting expression of timezone
     * e.g. "UTC", "Asia/Seoul"
     * ZoneId can be retrieve with ZoneId and ZoneOffset(subclass of ZoneId) class
     */
    @Test
    public void zoneIdTest() {
        System.out.println(ZoneId.systemDefault()); // "Asia/Seoul"
        System.out.println(ZoneId.of("Asia/Seoul")); // "Asia/Seoul"
        System.out.println(ZoneId.of("UTC")); // "UTC"
        System.out.println(ZoneOffset.UTC); // "Z", most easy waty to get UTC zone id
        System.out.println(ZoneId.ofOffset("UTC", ZoneOffset.ofHours(9))); // UTC+09:00
        System.out.println(ZoneId.ofOffset("", ZoneOffset.ofHours(9))); // +09:00

        System.out.println("\n# available ids: -----------------------");
        ZoneId.getAvailableZoneIds().forEach(id -> {
            if (id.startsWith("Asia"))
                System.out.println(id);
        });
    }

    /*
     * ZoneOffset extends ZoneId
     * ZoneOffset = ZoneId + Timezone offset string
     * 
     * time offset = String expression of gap from UTC
     *    e.g. "Z", "+09:00"
     */
    @Test
    public void zoneOffsetTest() {
        ZoneOffset zoneOffsetUTC = ZoneOffset.UTC;
        ZoneOffset zoneOffsetUTCString = ZoneOffset.of("Z");
        ZoneOffset zoneOffsetString = ZoneOffset.of("+9");
        ZoneOffset zoneOffsetHours = ZoneOffset.ofHours(9);

        System.out.println(zoneOffsetUTC); // "Z"
        System.out.println(zoneOffsetUTCString); // "Z"
        System.out.println(zoneOffsetString); // "+09:00"
        System.out.println(zoneOffsetHours); // "+09:00"

        // get system default ZoneOffset
        ZoneOffset systemDefaultZoneOffset1 = ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault())
                .getOffset();
        ZoneOffset systemDefaultZoneOffset2 = ZoneId.systemDefault().getRules().getOffset(Instant.now());

        System.out.println(systemDefaultZoneOffset1);
        System.out.println(systemDefaultZoneOffset2);
    }

    /*
     * java.time.Instant :
     *     nanosec UTC timepoint on timeline from epoch(UTC)
     *     Instan is UTC time value so can be transform with ZonedDateTime
     */
    @Test
    public void instantTest() {
        /* Generate Instant */
        System.out.println(Instant.now());
        System.out.println(Instant.parse("2024-08-02T00:38:29.616Z"));
        System.out.println(Date.from(Instant.parse("2024-08-02T00:38:29.616Z")));
        // Instant.atZone(ZoneId) change time to ZondId timezone 
        System.out.println(Instant.parse("2024-08-02T00:38:29.616Z").atZone(ZoneId.of("Asia/Seoul")));

        long epochMillisNowL = LocalDateTime.now()
                .toInstant(ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()).getOffset()).toEpochMilli();

        long epochMillisNowZ = ZonedDateTime.now().toInstant().toEpochMilli();

        /* compare Instant: Below methods use same internal method(Clock.currentInstant()) */
        Instant instantFromClockSystemTz = Clock.systemDefaultZone().instant();
        Instant instantFromClockUtcTz = Clock.systemUTC().instant();
        Instant instantFromInstance = Instant.now();
        System.out.println(instantFromClockSystemTz);
        System.out.println(instantFromClockUtcTz);
        System.out.println(instantFromInstance);

        /* Instant > ZonedDateTime */
        ZonedDateTime zonedDateTimeFromInstantUTC = Clock.systemUTC().instant().atZone(ZoneOffset.UTC);
        ZonedDateTime zonedDateTimeFromInstantLocal = Clock.systemDefaultZone().instant()
                .atZone(ZoneId.systemDefault());
        System.out.println(zonedDateTimeFromInstantUTC);
        System.out.println(zonedDateTimeFromInstantLocal);

        /* ZonedDateTime > Instant */
        Instant instantNowZ = Instant
                .ofEpochMilli(ZonedDateTime.now(ZoneId.systemDefault()).toInstant().toEpochMilli());

        /* LocalDateTime > Instant > ZonedDateTime */
        Instant instantNowL = Instant.ofEpochMilli(LocalDateTime.now()
                .toInstant(ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()).getOffset())
                .toEpochMilli());
        // atZone() transform instant value from UTC to designated timezone
        ZonedDateTime zonedDateTimeL = instantNowL.atZone(ZoneOffset.UTC);
    }

    @Test
    public void instantToString() {
        // OffsetDateTime, UTC
        var offsetDateTimeUTC = OffsetDateTime.ofInstant(Instant.now(), ZoneOffset.UTC).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        //var offsetDateTimeUTC = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(OffsetDateTime.ofInstant(Instant.now(), ZoneOffset.UTC));
        System.out.println("\nOffsetDateTimeUTC:\n"+offsetDateTimeUTC);

        // OffsetDateTime, System Default Timezone
        var offsetDateTimeLocalZ = OffsetDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        System.out.println("\nOffsetDateTimeLocalZ:\n"+offsetDateTimeLocalZ);

        // ZonedDateTime, UTC
        // DateTimeFormatter.ISO_ZONED_DATE_TIME.format(ZonedDateTime.ofInstant(Instant.now(), ZoneOffset.UTC))
        var zonedDateTimeUTC = ZonedDateTime.ofInstant(Instant.now(), ZoneOffset.UTC).format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
        System.out.println("\nZonedDateTimeUTC:\n"+zonedDateTimeUTC);

        // ZonedDateTime, System Default Timezone
        var zonedDateTimeLocalZ = ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss.SSSSSSSSSX"));
        System.out.println("\nZonedDateTimeLocalZone:\n" +zonedDateTimeLocalZ);
    }

    /*
     * https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/format/DateTimeFormatter.html
     * # DateTimeFormatter (ISO8601 Date/Time foramt)
     * uuuu-MM-ddTHH:mm:ss.SSSSSSSSS<ZoneOffset>[<ZoneId>]
     * 2024-07-31T13:51:33.110172867+09:00[Asia/Seoul]
     * 
     * DateTimeFormatter.ISO_LOCAL_DATE_TIME;  // '2011-12-03T10:15:30'
     * DateTimeFormatter.ISO_OFFSET_DATE_TIME; // '2011-12-03T10:15:30+01:00'
     * DateTimeFormatter.ISO_ZONED_DATE_TIME;  // '2011-12-03T10:15:30+01:00[Europe/Paris]'
     * DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss.SSSX"); // "2011-12-03T10:15:30.123Z"
     */
    @Test
    public void dateTimeFormatTest() {
        System.out.println(
                LocalDateTime.parse("2024-05-13T14:15:16.123456789",
                        DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        System.out.println(
                OffsetDateTime.parse("2024-05-13T14:15:16.123456789+09:00",
                        DateTimeFormatter.ISO_OFFSET_DATE_TIME));

        System.out.println(
                ZonedDateTime.parse("2024-05-13T14:15:16.123456789+09:00[Asia/Seoul]",
                        DateTimeFormatter.ISO_ZONED_DATE_TIME));

        String date = "2024-05-13";
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("uuuu-MM-dd");

        System.out.println(OffsetDateTime.of(LocalDate.parse(date, dateFormat),
                LocalTime.of(20, 30, 40),
                ZoneOffset.ofHoursMinutes(0, 0)));

        System.out.println(ZonedDateTime.of(LocalDate.parse(date, dateFormat),
                LocalTime.of(21, 31, 41),
                ZoneId.of("UTC")));
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

    @Test
    public void localDateTimeToInstant() {
        // ZoneOffset is child of ZoneId
        // Every type of DATETIME class can be converted through Instant
        var zid = "Asia/Seoul";
        var fromLocalTime = LocalDateTime.parse("2024-07-01T01:01:01");
        var toLocalTime = LocalDateTime.parse("2024-08-01T01:01:01");
        var fromInstant = fromLocalTime.toInstant(ZoneId.of(zid).getRules().getOffset(fromLocalTime));
        var toInstant = toLocalTime.toInstant(ZoneId.of(zid).getRules().getOffset(toLocalTime));

        System.out.println(fromInstant);
        System.out.println(toInstant);
        System.out.println(ZonedDateTime.ofInstant(fromInstant, ZoneOffset.UTC).toString());
        System.out.println(ZonedDateTime.ofInstant(toInstant, ZoneOffset.UTC).toString());
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

        System.out.println(zoneTime); // 2024-05-13T21:31:41Z[UTC]

        // transform localtime+timezone based on given timezone
        System.out.println(zoneTime.withZoneSameInstant(ZoneId.of("Asia/Seoul"))); // 2024-05-14T06:31:41+09:00[Asia/Seoul]

        // transform timezone only (localtime x)
        System.out.println(zoneTime.withZoneSameLocal(ZoneId.of("Asia/Seoul"))); // 2024-05-13T21:31:41+09:00[Asia/Seoul]
    }

    @Test
    public void dateTest() {
        // Date.from(Instant)
        Date fromInstant = Date.from(Instant.parse("2024-08-02T00:38:29.616Z"));

        Date fromLDT = Date.from(LocalDateTime.parse("2024-08-01T04:50:09.511",
                DateTimeFormatter.ISO_LOCAL_DATE_TIME).atZone(ZoneOffset.UTC).toInstant());

        Date fromODT = Date.from(OffsetDateTime.parse("2024-08-01T04:50:09.511+09:00",
                DateTimeFormatter.ISO_OFFSET_DATE_TIME).toInstant());

        Date fromZDT = Date.from(ZonedDateTime.parse("2024-08-01T04:50:09.511+09:00[Asia/Seoul]",
                DateTimeFormatter.ISO_ZONED_DATE_TIME).toInstant());
    }
}
