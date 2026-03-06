package com.example.sample.utils;

/**
 * Utility class for IPv4 address operations.
 * Provides methods to validate, convert, and check if IP addresses are within specific ranges.
 */
public class Ip4Util {

    /**
     * Converts an IPv4 address string to a long value
     * 
     * @param ipAddress IPv4 address (e.g., "192.168.1.1")
     * @return IP address converted to long value
     * @throws IllegalArgumentException if the IP address format is invalid
     */
    public static long ipToLong(String ipAddress) {
        if (ipAddress == null || ipAddress.isEmpty()) {
            throw new IllegalArgumentException("IP address cannot be null or empty.");
        }

        String[] octets = ipAddress.split("\\.");
        if (octets.length != 4) {
            throw new IllegalArgumentException("Invalid IPv4 address format: " + ipAddress);
        }

        long result = 0;
        for (int i = 0; i < 4; i++) {
            try {
                int octet = Integer.parseInt(octets[i]);
                if (octet < 0 || octet > 255) {
                    throw new IllegalArgumentException("Each octet must be in the range 0-255: " + octet);
                }
                result = (result << 8) | octet;
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid IPv4 address format: " + ipAddress, e);
            }
        }

        return result;
    }

    /**
     * Converts a long value to an IPv4 address string
     * 
     * @param ip IP address represented as a long value
     * @return IPv4 address string (e.g., "192.168.1.1")
     */
    public static String longToIp(long ip) {
        return ((ip >> 24) & 0xFF) + "." +
               ((ip >> 16) & 0xFF) + "." +
               ((ip >> 8) & 0xFF) + "." +
               (ip & 0xFF);
    }

    /**
     * Checks if an IP address is within a CIDR notation IP range
     * 
     * @param ipAddress IP address to check (e.g., "192.168.1.100")
     * @param cidr IP range in CIDR notation (e.g., "192.168.1.0/24")
     * @return true if the IP address is within the range, false otherwise
     * @throws IllegalArgumentException if the IP address or CIDR format is invalid
     */
    public static boolean isInRange(String ipAddress, String cidr) {
        if (cidr == null || !cidr.contains("/")) {
            throw new IllegalArgumentException("Invalid CIDR format: " + cidr);
        }

        String[] parts = cidr.split("/");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid CIDR format: " + cidr);
        }

        String networkAddress = parts[0];
        int prefixLength;
        try {
            prefixLength = Integer.parseInt(parts[1]);
            if (prefixLength < 0 || prefixLength > 32) {
                throw new IllegalArgumentException("CIDR prefix length must be in the range 0-32: " + prefixLength);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid CIDR prefix length: " + parts[1], e);
        }

        long ip = ipToLong(ipAddress);
        long network = ipToLong(networkAddress);
        
        // Generate subnet mask
        long mask = (prefixLength == 0) ? 0 : (-1L << (32 - prefixLength));
        
        // Check if IP address and network address belong to the same network
        return (ip & mask) == (network & mask);
    }

    /**
     * Checks if an IP address is within a range between start and end IP addresses
     * 
     * @param ipAddress IP address to check (e.g., "192.168.1.100")
     * @param startIp Starting IP address (e.g., "192.168.1.1")
     * @param endIp Ending IP address (e.g., "192.168.1.254")
     * @return true if the IP address is within the range, false otherwise
     * @throws IllegalArgumentException if the IP address format is invalid or start IP is greater than end IP
     */
    public static boolean isInRange(String ipAddress, String startIp, String endIp) {
        long ip = ipToLong(ipAddress);
        long start = ipToLong(startIp);
        long end = ipToLong(endIp);

        if (!isStartIpLessOrEqualThanEndIp(startIp, endIp)) {
            throw new IllegalArgumentException("Start IP is greater than end IP: " + startIp + " > " + endIp);
        }

        return ip >= start && ip <= end;
    }

    /**
     * Validates whether the start IP is less or equal than the end IP
     * 
     * @param startIp Starting IP address
     * @param endIp Ending IP address
     * @return true if start IP is less or equal than end IP, false otherwise
     * @throws IllegalArgumentException if either IP address format is invalid
     */
    public static boolean isStartIpLessOrEqualThanEndIp(String startIp, String endIp) {
        long start = ipToLong(startIp);
        long end = ipToLong(endIp);
        return start <= end;
    }

    /**
     * Validates if an IP address is in valid IPv4 format
     * 
     * @param ipAddress IP address to validate
     * @return true if valid IPv4 address, false otherwise
     */
    public static boolean isValidIpv4(String ipAddress) {
        if (ipAddress == null || ipAddress.isEmpty()) {
            return false;
        }

        String[] octets = ipAddress.split("\\.");
        if (octets.length != 4) {
            return false;
        }

        for (String octet : octets) {
            try {
                int value = Integer.parseInt(octet);
                if (value < 0 || value > 255) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }

        return true;
    }

    /**
     * Validates if a CIDR notation is valid
     * 
     * @param cidr CIDR notation to validate (e.g., "192.168.1.0/24")
     * @return true if valid CIDR notation, false otherwise
     */
    public static boolean isValidCidr(String cidr) {
        if (cidr == null || !cidr.contains("/")) {
            return false;
        }

        String[] parts = cidr.split("/");
        if (parts.length != 2) {
            return false;
        }

        if (!isValidIpv4(parts[0])) {
            return false;
        }

        try {
            int prefixLength = Integer.parseInt(parts[1]);
            return prefixLength >= 0 && prefixLength <= 32;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Calculates the network address from CIDR notation
     * 
     * @param cidr IP range in CIDR notation (e.g., "192.168.1.0/24")
     * @return Network starting IP address
     * @throws IllegalArgumentException if the CIDR format is invalid
     */
    public static String getNetworkAddress(String cidr) {
        if (!isValidCidr(cidr)) {
            throw new IllegalArgumentException("Invalid CIDR format: " + cidr);
        }

        String[] parts = cidr.split("/");
        long ip = ipToLong(parts[0]);
        int prefixLength = Integer.parseInt(parts[1]);
        
        long mask = (prefixLength == 0) ? 0 : (-1L << (32 - prefixLength));
        long networkAddress = ip & mask;
        
        return longToIp(networkAddress);
    }

    /**
     * Calculates the broadcast address from CIDR notation
     * 
     * @param cidr IP range in CIDR notation (e.g., "192.168.1.0/24")
     * @return Network broadcast IP address
     * @throws IllegalArgumentException if the CIDR format is invalid
     */
    public static String getBroadcastAddress(String cidr) {
        if (!isValidCidr(cidr)) {
            throw new IllegalArgumentException("Invalid CIDR format: " + cidr);
        }

        String[] parts = cidr.split("/");
        long ip = ipToLong(parts[0]);
        int prefixLength = Integer.parseInt(parts[1]);
        
        long mask = (prefixLength == 0) ? 0 : (-1L << (32 - prefixLength));
        long broadcastAddress = ip | ~mask;
        
        return longToIp(broadcastAddress);
    }
}
