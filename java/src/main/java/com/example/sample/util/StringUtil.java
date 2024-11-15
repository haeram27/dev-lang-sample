package com.example.sample.util;

import java.util.Locale;
import java.util.Objects;

public final class StringUtil {

    private StringUtil() {
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
        return null == src ? null : src.toLowerCase(Locale.getDefault());
    }

    /**
     * 
     * @param src
     * @return
     */
    public static String toUpperCase(String src) {
        return null == src ? null : src.toUpperCase(Locale.getDefault());
    }

    public static String toString(Object obj) {
        return obj == null ? null : obj.toString();
    }
}