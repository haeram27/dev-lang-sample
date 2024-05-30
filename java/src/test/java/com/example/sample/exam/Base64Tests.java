package com.example.sample.exam;

import java.util.Base64;

import org.junit.jupiter.api.Test;

public class Base64Tests {

    @Test
    void base64Test() {
        byte[] originalBytes = new byte[] { (byte) 255, (byte) 128, (byte) 64, (byte) 32, (byte) 16, (byte) 8, (byte) 4,
                (byte) 2, (byte) 1 };

        for (var b : originalBytes) {
            System.out.println(Integer.toBinaryString(Byte.toUnsignedInt(b)));
        }

        String base64Encoded = Base64.getEncoder().encodeToString(originalBytes);
        System.out.println(base64Encoded);
        byte[] base64Decoded = Base64.getDecoder().decode(base64Encoded);
    }
}
