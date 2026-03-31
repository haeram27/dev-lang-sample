package com.example.sample.util;

import java.util.Locale;
import java.util.Objects;

public final class StringUtil {

    private StringUtil() {
        // Prevent instantiation
    }

    public static boolean hasText(String text) {
        return text != null && !text.isBlank();
    }

    public static boolean isEmpty(String text) {
        return Objects.isNull(text) || text.isEmpty();
    }

    public static boolean nonEmpty(String text) {
        return Objects.nonNull(text) && !text.isEmpty();
    }

    /**
     * Convert String to long
     * 
     * @param value value
     * @param def default value
     * @return long
     */
    public static long toLong(String value, long def) {
        if (isEmpty(value)) {
            return def;
        }

        try {
            return Long.valueOf(value);
        } catch (NumberFormatException e) {
            return def;
        }
    }

    /**
     * Convert String to int
     * 
     * @param value value
     * @param def default value
     * @return int
     */
    public static int toInt(String value, int def) {
        if (isEmpty(value)) {
            return def;
        }
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            return def;
        }
    }

    /**
     * 
     * @param src
     * @return
     */
    public static String toLowerCase(String src) {
        return hasText(src) ? src.toLowerCase(Locale.getDefault()) : null;
    }

    /**
     * 
     * @param src
     * @return
     */
    public static String toUpperCase(String src) {
        return hasText(src) ? src.toUpperCase(Locale.getDefault()) : null;
    }

    public static String toString(Object obj) {
        return Objects.toString(obj, null);
    }
}