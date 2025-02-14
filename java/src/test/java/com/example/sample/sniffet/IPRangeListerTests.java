package com.example.sample.sniffet;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

public class IPRangeListerTests {
    private static final Pattern IP_RANGE_PATTERN = Pattern.compile(
            "^(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])\\."
                + "(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])\\."
                + "(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])\\."
                + "(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])"
                + "\\-(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])\\."
                + "(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])\\."
                + "(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])\\."
                + "(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])$");

    @Test
    void run() {
        String ipRange = "1.1.1.245-1.1.2.2";

        List<String> ipList = getIPRangeList(ipRange);

        for (String ip : ipList) {
            System.out.println(ip);
        }
    }

    public static List<String> getIPRangeList(String range) {
        List<String> ipList = new ArrayList<>();

        if (!IP_RANGE_PATTERN.matcher(range.strip()).matches()) {
            throw new IllegalArgumentException("Invalid IP range format");
        }

        String[] parts = range.strip().split("-");

        try {
            InetAddress startInet = InetAddress.getByName(parts[0].strip());
            InetAddress endInet = InetAddress.getByName(parts[1].strip());

            byte[] startBytes = startInet.getAddress();
            byte[] endBytes = endInet.getAddress();

            int start = ((startBytes[0] & 0xFF) << 24) | ((startBytes[1] & 0xFF) << 16) | ((startBytes[2] & 0xFF) << 8)
                    | (startBytes[3] & 0xFF);
            int end = ((endBytes[0] & 0xFF) << 24) | ((endBytes[1] & 0xFF) << 16) | ((endBytes[2] & 0xFF) << 8)
                    | (endBytes[3] & 0xFF);

            for (int i = start; i <= end; i++) {
                int a = (i >> 24) & 0xFF;
                int b = (i >> 16) & 0xFF;
                int c = (i >> 8) & 0xFF;
                int d = i & 0xFF;
                ipList.add(a + "." + b + "." + c + "." + d);
            }
        } catch (UnknownHostException e) {
            throw new RuntimeException("Invalid IP address", e);
        }

        return ipList;
    }
}
