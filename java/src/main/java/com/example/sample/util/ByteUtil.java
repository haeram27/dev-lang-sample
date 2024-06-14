package com.example.sample.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public final class ByteUtil {

    private ByteUtil() {
    }

    public static byte[] xor(byte[] x, byte[] y) {
        byte[] res = null;
        if (x != null && y != null) {
            int sz = (x.length < y.length ? x.length : y.length);
            res = new byte[sz];

            for (int i = 0; i < sz; i++) {
                res[i] = (byte) (x[i] ^ y[i]);
            }
        }

        return res;
    }

    public static byte[] xorPadding(byte[] x, byte[] y) {
        byte[] res = null;
        if (x != null && y != null) {
            int xsz = x.length;
            int ysz = y.length;

            int sz = (xsz > ysz ? xsz : ysz);
            res = new byte[sz];

            for (int i = 0; i < sz; i++) {
                byte xb;
                byte yb;

                if (i >= xsz) {
                    xb = 0;
                } else {
                    xb = x[i];
                }
                if (i >= ysz) {
                    yb = 0;
                } else {
                    yb = y[i];
                }

                res[i] = (byte) (xb ^ yb);
            }
        }

        return res;
    }

    public static String toHexString(byte[] bytes) {
        if (bytes == null) {
            return null;
        }

        StringBuffer res = new StringBuffer();
        for (byte b : bytes) {
            res.append(Integer.toString((b & 0xf0) >> 4, 16));
            res.append(Integer.toString(b & 0x0f, 16));
        }
        return res.toString();
    }

    public static byte[] intToBytes(int n, ByteOrder order) {
        ByteBuffer buf = ByteBuffer.allocate(Integer.BYTES);
        buf.order(order);
        buf.putInt(n);
        return buf.array();
    }

    public static byte[] intToBytes(int n) {
        return ByteBuffer.allocate(Integer.BYTES).putInt(n).array();
    }

    public static int bytesToInt(byte[] b, ByteOrder order) {
        ByteBuffer buf = ByteBuffer.wrap(b);
        buf.order(order);
        return buf.getInt();
    }

    public static int bytesToInt(byte[] b) {
        return ByteBuffer.wrap(b).getInt();
    }

    public static long bytesToUnsignedInt(byte[] b, ByteOrder order) {
        ByteBuffer buf = ByteBuffer.allocate(Long.BYTES);
        buf.putInt(0);
        buf.put(b);
        buf.order(order);
        buf.flip();
        return buf.getLong();
    }

    public static long bytesToUnsignedInt(byte[] b) {
        ByteBuffer buf = ByteBuffer.allocate(Long.BYTES);

        buf.putInt(0);
        buf.put(b);
        buf.flip();
        return buf.getLong();
    }

    public static boolean equals(byte[] source, byte[] compare) {
        return Arrays.equals(source, compare);
    }

    public static byte[] toByteArray(InputStream stream) throws IOException {
        try (ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {

            int read;
            byte[] temp = new byte[4096];

            while ((read = stream.read(temp)) != -1) {
                buffer.write(temp, 0, read);
            }

            buffer.flush();
            return buffer.toByteArray();
        }
    }
}