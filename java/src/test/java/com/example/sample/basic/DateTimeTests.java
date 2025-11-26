package com.example.sample.basic;

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

    /*
     * DateTime types
     * common attr:
     *     based on ISO-8601
     *     minimum time unit = nano seconds
     * 
     * java.time.Instant :
     *     NANO seconds UTC timepoint on timeline from epoch(UTC) 1970-01-01T00:00:00Z
     *     Instan is UTC time value so can be transform to (Local/Offset/Zoned)DateTime
     *     most useful time format to save and transfer time between modules.
     *     timezone info = UTC based
     *     for record(db,file)/network transfer, easy to operation
     * 
     * java.time.LocalDateTime :
     *     timezone info = none
     *     for business logic (UI)
     * 
     * java.time.OffsetDateTime :
     *     timezone info = time offset(+-) from UTC
     *     for business logic (UI)
     * 
     * java.time.ZonedDateTime :
     *     timezone info = ZoneId (timezone/city)
     *     support DST(Daylight Saving Time) rule applicable 
     *     for business logic (UI)
     * 
     * java.time.ZoneOffset extends ZoneId
     *     ZoneOffset.UTC == ZoneId.of("UTC") == ZoneId.of("Z");
     * 
     * 
     * 
     */

    @Test
    public void nowTest() {
        // !!! All of now() return same time value(instant)
        Instant instantNowUTC = Instant.now(); // == ZonedDateTime.now(ZoneOffset.UTC).toInstant();
        System.out.println(instantNowUTC); // 2024-07-31T05:53:12.727779080Z


        System.out.println("\n\n");
        var offsetDateTimeNowSystemDefaultTimeZone = OffsetDateTime.now();            // system default timezone
        var offsetDateTimeNowUTCZoneOffset = OffsetDateTime.now(ZoneOffset.UTC);      // UTC
        var offsetDateTimeNowUTCZoneId = OffsetDateTime.now(ZoneId.of("UTC")); // UTC
        System.out.println(offsetDateTimeNowSystemDefaultTimeZone); // 2025-09-05T12:47:07.894101049+09:00
        System.out.println(offsetDateTimeNowUTCZoneOffset);         // 2025-09-05T03:47:07.894143781Z
        System.out.println(offsetDateTimeNowUTCZoneId);             // 2025-09-05T03:47:07.894166026Z


        System.out.println("\n\n");
        var zonedDateTimeNowSystemDefaultTimeZone = ZonedDateTime.now(); // system default timezone
        var zonedDateTimeNowUTCZoneOffset = ZonedDateTime.now(ZoneOffset.UTC); // UTC
        var zonedDateTimeNowUTCZoneId = ZonedDateTime.now(ZoneId.of("UTC")); // UTC
        System.out.println(zonedDateTimeNowSystemDefaultTimeZone); // 2024-07-31T14:53:12.727779080+09:00[Asia/Seoul]
        System.out.println(zonedDateTimeNowUTCZoneOffset);         // 2024-07-31T05:53:12.727779080Z
        System.out.println(zonedDateTimeNowUTCZoneId);             // 2024-07-31T05:53:12.727779080Z[UTC]

        System.out.println("\n\n");
        var localDateTimeNow = LocalDateTime.now();
        System.out.println(localDateTimeNow); // 2024-07-31T14:53:12.727779080
        System.out.println(zonedDateTimeNowUTCZoneOffset.toLocalDateTime()); // no time transform, just diposal timezone info
        System.out.println(zonedDateTimeNowSystemDefaultTimeZone.toLocalDateTime()); // no time transform, just diposal timezone info

        // below three calls returns same Instant from system clock
        // time values are same but expression of time is changed with given ZoneId
        System.out.println(ZonedDateTime.now()); // same as ZoneId.systemDefalut()
        System.out.println(ZonedDateTime.now(ZoneId.systemDefault()));
        System.out.println(ZonedDateTime.now(ZoneOffset.UTC));

        // below four calls returns same Instant from system clock
        System.out.println(ZonedDateTime.now().toInstant());
        System.out.println(ZonedDateTime.now(ZoneId.systemDefault()).toInstant());
        System.out.println(ZonedDateTime.now(ZoneOffset.UTC).toInstant());
        System.out.println(LocalDateTime.now().toInstant(ZoneId.systemDefault().getRules().getOffset(Instant.now()))); // same as ZoneId.systemDefalut()
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
        // java.time.ZoneOffset extends ZoneId
        // ZoneOffset.UTC == ZoneOffset.of("UTC") == ZoneOffset.of("Z")
        //    == ZoneId.of("UTC") == ZoneId.of("Z")

        ZoneOffset zoneOffsetUTC = ZoneOffset.UTC;
        ZoneOffset zoneOffsetUTCString = ZoneOffset.of("Z");
        ZoneOffset zoneOffsetString = ZoneOffset.of("+9");
        ZoneOffset zoneOffsetHours = ZoneOffset.ofHours(9);

        System.out.println(zoneOffsetUTC); // "Z"
        System.out.println(zoneOffsetUTCString); // "Z"
        System.out.println(zoneOffsetString); // "+09:00"
        System.out.println(zoneOffsetHours); // "+09:00"

        // get system default ZoneOffset
        ZoneOffset systemDefaultZoneOffset1 = ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()).getOffset();
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

        System.out.println("\n\n");
        long epochMillisNowZ = Instant.now().toEpochMilli();
        System.out.println("nowEpochTimeMilli : " + epochMillisNowZ);


        System.out.println("\n\n");
        /* compare Instant: Below methods use same internal method(Clock.currentInstant())
         * == All Same Instant time
         */
        Instant instantFromInstance = Instant.now();
        Instant instantFromClockSystemTz = Clock.systemDefaultZone().instant();
        Instant instantFromClockUtcTz = Clock.systemUTC().instant();
        System.out.println("instantFromInstance     : " + instantFromInstance);
        System.out.println("instantFromClockSystemTz: " + instantFromClockSystemTz);
        System.out.println("instantFromClockUtcTz   : " + instantFromClockUtcTz);


        System.out.println("\n\n");
        /* Instant > OffsetDateTime */
        //var odt = OffsetDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
        var odt = OffsetDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
        System.out.println("OffsetDateTime From Instant: " + odt);

        /* Instant > ZonedDateTime */
        // var zdt = ZonedDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
        var zdt = Instant.now().atZone(ZoneId.of("Asia/Seoul"));
        System.out.println("ZonedDateTime From Instant : " + zdt);

        /* Instant > LocalDateTime */
        var ldt = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
        System.out.println("LocalDateTime From Instant : " + ldt);

        System.out.println("\n\n");
        /* OffsetDateTime > Instant */
        var instantFromOffsetDateTime = OffsetDateTime.now(ZoneOffset.UTC).toInstant();
        System.out.println("Instant From OffsetDateTime: " + instantFromOffsetDateTime);

        /* ZonedDateTime > Instant */
        // var instantFromZonedDateTime = ZonedDateTime.now(ZoneOffset.UTC).toInstant();
        var instantFromZonedDateTime = ZonedDateTime.now(ZoneId.systemDefault()).toInstant();
        System.out.println("Instant From ZonedDateTime : " + instantFromZonedDateTime);

        /* LocalDateTime > Instant */
        var instantFromLocalDateTime = LocalDateTime.now().toInstant(ZoneOffset.UTC);
        System.out.println("Instant From LocalDateTime : " + instantFromLocalDateTime);

    }

    @Test
    public void instantToString() {

        System.out.println(DateTimeFormatter.ISO_INSTANT.format(Instant.now()));

        // OffsetDateTime, UTC
        var offsetDateTimeUTC = OffsetDateTime.ofInstant(Instant.now(), ZoneOffset.UTC).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        //var offsetDateTimeUTC = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(OffsetDateTime.ofInstant(Instant.now(), ZoneOffset.UTC));
        System.out.println("\nOffsetDateTimeUTC: " + offsetDateTimeUTC);

        // OffsetDateTime, System Default Timezone
        var offsetDateTimeLocalZ = OffsetDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        System.out.println("\nOffsetDateTimeLocalZ: " + offsetDateTimeLocalZ);

        // ZonedDateTime, UTC
        // DateTimeFormatter.ISO_ZONED_DATE_TIME.format(ZonedDateTime.ofInstant(Instant.now(), ZoneOffset.UTC))
        var zonedDateTimeUTC = ZonedDateTime.ofInstant(Instant.now(), ZoneOffset.UTC).format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
        System.out.println("\nZonedDateTimeUTC: " + zonedDateTimeUTC);

        // ZonedDateTime, System Default Timezone
        var zonedDateTimeLocalZ = ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss.SSSSSSSSSX"));
        System.out.println("\nZonedDateTimeLocalZone:" + zonedDateTimeLocalZ);
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

        // format Instant to ISO DateTime String
        System.out.println("ISO_INSTANT: " +
            DateTimeFormatter.ISO_INSTANT.format(Instant.now()));

        System.out.println("ISO_LOCAL_DATE_TIME: " +
            DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(
                LocalDateTime.ofInstant(Instant.now(), ZoneOffset.systemDefault())));

        System.out.println("ISO_OFFSET_DATE_TIME: " +
            DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(
                OffsetDateTime.ofInstant(Instant.now(), ZoneOffset.systemDefault())));

        System.out.println("ISO_ZONED_DATE_TIME: " +
            DateTimeFormatter.ISO_ZONED_DATE_TIME.format(
                ZonedDateTime.ofInstant(Instant.now(), ZoneOffset.systemDefault())));


        // parse ISO DateTime String
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
        var zdt = ZonedDateTime.of(LocalDate.parse(date, dateFormat), LocalTime.of(21, 31, 41),
                ZoneId.of("UTC"));

        System.out.println(zdt); // 2024-05-13T21:31:41Z[UTC]

        // transform localtime+timezone based on given timezone
        System.out.println(zdt.withZoneSameInstant(ZoneId.of("Asia/Seoul"))); // 2024-05-14T06:31:41+09:00[Asia/Seoul]

        // transform timezone only (localtime x)
        System.out.println(zdt.withZoneSameLocal(ZoneId.of("Asia/Seoul"))); // 2024-05-13T21:31:41+09:00[Asia/Seoul]
    }

    @Test
    public void instantToZonedDateTimeWithDstTest() {
        Instant instant = Instant.now();
        System.out.println("Instant (UTC): " + instant);

        // 2. timezone
        ZoneId laZone = ZoneId.of("America/Los_Angeles");

        // 3. apply timezone and DST rule = convert to ZonedDateTime (LA timezone, include DST rule)
        ZonedDateTime zonedDateTimeLa = instant.atZone(laZone);

        // 4. output
        System.out.println("Los Angeles ZonedDateTime: " + zonedDateTimeLa);
        System.out.println("timezone: " + zonedDateTimeLa.getZone());
        System.out.println("is DST applied: "
                + zonedDateTimeLa.getZone().getRules().isDaylightSavings(zonedDateTimeLa.toInstant()));
    }


    /*
     * OffsetDateTime to ZonedDateTime
     */
    @Test
    public void offsetDateTimeToZonedDateTimeWithDstTest() {
        var odt = OffsetDateTime.now();
        System.out.println("Instant (UTC): " + odt.toInstant());
        System.out.println("OffsetDateTime.toString : " + odt.toString());
        System.out.println("OffsetDateTime.formatter: " + odt.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));

        ZoneId laZone = ZoneId.of("America/Los_Angeles");
        
        // DST rule NOT applied
        ZonedDateTime toZonedDateTime = odt.toZonedDateTime();

        // DST rule applied = timezone converting (LA timezone, include DST rule) affects localtime
        // preserve Instant between OffsetDateTime and ZonedDateTime
        ZonedDateTime atZoneSameInstant = odt.atZoneSameInstant(laZone);

        // apply timezone but do not change local-date-time
        ZonedDateTime atZoneSimilarLocal = odt.atZoneSimilarLocal(laZone);

        // 4. output
        System.out.println("\n\n");
        System.out.println("## toZonedDateTime");
        System.out.println("Los Angeles ZonedDateTime: " + toZonedDateTime);
        System.out.println("timezone: " + toZonedDateTime.getZone());
        System.out.println("is DST applied: "
                + toZonedDateTime.getZone().getRules().isDaylightSavings(toZonedDateTime.toInstant()));

        System.out.println("\n\n");
        System.out.println("## atZoneSameInstant");
        System.out.println("Los Angeles ZonedDateTime: " + atZoneSameInstant);
        System.out.println("timezone: " + atZoneSameInstant.getZone());
        System.out.println("is DST applied: "
                + atZoneSameInstant.getZone().getRules().isDaylightSavings(atZoneSameInstant.toInstant()));

        System.out.println("\n\n");
        System.out.println("## atZoneSimilarLocal");
        System.out.println("Los Angeles ZonedDateTime: " + atZoneSimilarLocal);
        System.out.println("timezone: " + atZoneSimilarLocal.getZone());
        System.out.println("is DST applied: "
                + atZoneSimilarLocal.getZone().getRules().isDaylightSavings(atZoneSimilarLocal.toInstant()));
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
