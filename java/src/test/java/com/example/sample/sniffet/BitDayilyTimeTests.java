package com.example.sample.sniffet;

import java.util.Base64;
import org.junit.jupiter.api.Test;

public class BitDayilyTimeTests {

    void printb(byte[] b) {
        Byte B;
        for (int h = 0; h < b.length; h++) {
            B = Byte.valueOf(b[h]);
            if (B > 0) {
                System.out.println(
                        String.format("%d:_%8s", h, Integer.toBinaryString(Byte.toUnsignedInt(B))).replace(" ", "0"));
            }
        }
    }

    byte[] recordMaker() {
        Byte B;
        byte b[] = new byte[24];
        for (int h = 0; h < b.length; h++) {
            for (int m = 0; m < 6; m++) {
                b[h] |= (byte) (0x01 << m);
            }
            B = Byte.valueOf(b[h]);
            System.out.println(String.format("%8s", Integer.toBinaryString(Byte.toUnsignedInt(B))).replace(" ", "0"));
        }

        return b;
    }

    boolean setRecord(byte[] dayRecord, int hour, int min) {
        if (dayRecord.length < 24)
            return false;
        if (hour < 0 || hour > 23)
            return false;
        if (min < 0 || min > 59)
            return false;

        dayRecord[hour] |= (byte) (0x01 << min / 10);

        return true;
    }

    int getDayRecordHours(byte[] dayRecord) {
        int hours = 0;
        for (int h = 0; h < dayRecord.length; h++) {
            if (dayRecord[h] > 0) {
                hours++;
            }
        }
        return hours;
    }

    int getDayRecordMins(byte[] dayRecord) {
        int mins = 0;
        byte flag = (byte) 0;
        for (int h = 0; h < dayRecord.length; h++) {
            for (int m = 0; m < 6; m++) {
                flag = (byte) (dayRecord[h] & (byte) (0x01 << m));
                if (flag > 0) {
                    mins++;
                }
            }
        }
        return mins;
    }


    @Test
    void recordTobase64Test() {
        byte b[] = new byte[24];
        for (int h = 0; h < b.length; h++) {
            for (int m = 0; m < 4; m++) {
                b[h] |= (byte) (0x01 << m);
            }
        }

        String base64Encoded = Base64.getEncoder().encodeToString(b);
        System.out.println(base64Encoded);
        System.out.println(base64Encoded.length());
    }

    @Test
    void run() {

        byte[] b = recordMaker();
        getDayRecordHours(b);
        getDayRecordMins(b);

    }

    @Test
    void run2() {
        byte[] b = new byte[24];
        setRecord(b, 0, 13);
        setRecord(b, 23, 0);
        printb(b);
    }
}
