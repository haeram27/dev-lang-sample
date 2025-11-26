package com.example.sample.sniffet;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class IPRangeListerTests {
    private static final Pattern IP_RANGE_PATTERN = Pattern.compile(
            // start ip4
            "^(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])\\."
                + "(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])\\."
                + "(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])\\."
                + "(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])"
            // end ip4
                + "\\-(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])\\."
                + "(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])\\."
                + "(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])\\."
                + "(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])$");

    public static List<String> getIPRangeList(String range) {
        return getIPRangeList(range, 100);
    }

    /*
     * generate ip list with in maxCount
     * ex) getIPRangeList("1.1.1.1-1.1.1.255", 10)
     */
    public static List<String> getIPRangeList(String range, int maxCount) {
        List<String> ipList = new ArrayList<>();

        if (maxCount > 10000) {
            throw new IllegalArgumentException("maxCount is over 10000");
        }

        // if (!IP_RANGE_PATTERN.matcher(range.strip()).matches()) {
        //     throw new IllegalArgumentException("Invalid IP range format");
        // }

        String[] parts = range.strip().split("-");

        try {
            InetAddress startInet = InetAddress.getByName(parts[0].strip());
            InetAddress endInet = InetAddress.getByName(parts[1].strip());

            byte[] startBytes = startInet.getAddress();
            byte[] endBytes = endInet.getAddress();

            long start = ((long)(startBytes[0] & 0xFF) << 24) | ((startBytes[1] & 0xFF) << 16) | ((startBytes[2] & 0xFF) << 8)
                    | (startBytes[3] & 0xFF);
            long end = ((long)(endBytes[0] & 0xFF) << 24) | ((endBytes[1] & 0xFF) << 16) | ((endBytes[2] & 0xFF) << 8)
                    | (endBytes[3] & 0xFF);

            for (long i = start; i <= end && ipList.size() < maxCount; i++) {
                int a = (int)((i >> 24) & 0xFF);
                int b = (int)((i >> 16) & 0xFF);
                int c = (int)((i >> 8) & 0xFF);
                int d = (int)(i & 0xFF);
                ipList.add(a + "." + b + "." + c + "." + d);
            }
        } catch (UnknownHostException e) {
            throw new RuntimeException("Invalid IP address", e);
        }

        return ipList;
    }

    @Test
    void run() {
        final String ipRangeMax = "0.0.0.0-255.255.255.255"; // count = Math.pow(256,4) = 4,294,967,296
        final String ipRange = "1.1.1.245-1.1.2.2";

        boolean printLog = true;
        boolean printFile = false;
        boolean printJson = false;

        List<String> ipList = getIPRangeList(ipRangeMax, 3000);

        // print to console
        if (printLog) {
            for (String ip : ipList) {
                System.out.println(ip);
            }
        }

        // print to file (project root)
        if (printFile) {
            try {
                Path outputPath = Path.of("ip_list.txt");
                Files.write(outputPath, ipList, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                System.out.println("\nIP list written to: " + outputPath.toAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // print to json (project root)
        if (printJson) {
            JsonMapper mapper = new JsonMapper();
            ObjectNode root = mapper.createObjectNode();
            ArrayNode ips = root.putArray("ipList");
            
            // age 1부터 100까지
            for (var ip : ipList) {
                ObjectNode ipNode = mapper.createObjectNode();
                ipNode.put("ip", ip);
                ips.add(ipNode);
            }

            try {
                String result = mapper.writeValueAsString(root);
                System.out.println(result);
                
                // 파일로 저장
                Files.writeString(Path.of("ip_list.json"), result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("number of ip: " + ipList.size());
    }
}
