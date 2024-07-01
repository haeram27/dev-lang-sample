package com.example.sample.exam;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.junit.jupiter.api.Test;

public class Base64Tests {

    @Test
    void base64TempTest() {
        byte[] originalBytes = new byte[24];
        for (int i = 0; i < 24; i++) {
            if (i % 2 == 0) {
                originalBytes[i] = (byte) 1;
            }
        }

        for (var b : originalBytes) {
            System.out.println(Integer.toBinaryString(Byte.toUnsignedInt(b)));
        }

        String base64Encoded = Base64.getEncoder().encodeToString(originalBytes);
        System.out.println(base64Encoded);
        byte[] base64Decoded = Base64.getDecoder().decode(base64Encoded);
    }

    @Test
    void base64Test() {
        byte[] originalBytes = new byte[] {(byte) 255, (byte) 128, (byte) 64, (byte) 32, (byte) 16, (byte) 8, (byte) 4,
                (byte) 2, (byte) 1};

        for (var b : originalBytes) {
            System.out.println(Integer.toBinaryString(Byte.toUnsignedInt(b)));
        }

        String base64Encoded = Base64.getEncoder().encodeToString(originalBytes);
        System.out.println(base64Encoded);
        byte[] base64Decoded = Base64.getDecoder().decode(base64Encoded);
    }

    @Test
    void base64StringEncodeTest() {
        String plain = "xxxxxx<iframe src=\"javascript:alert(parent.document.domain)\">22222";
        String encoded = Base64.getEncoder().encodeToString(plain.getBytes(StandardCharsets.UTF_8));
        System.out.println("## decoded:----");
        System.out.println(encoded);

        String decoded = new String(Base64.getDecoder().decode(encoded.getBytes(StandardCharsets.UTF_8)),
                StandardCharsets.UTF_8);
        System.out.println("## decoded:----");
        System.out.println(decoded);
    }
}
