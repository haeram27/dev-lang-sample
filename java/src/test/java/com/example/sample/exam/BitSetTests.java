package com.example.sample.exam;

import java.util.BitSet;
import org.junit.jupiter.api.Test;

public class BitSetTests {
    @Test
    void BitsetTest() {
        byte[] originalBytes = new byte[] {(byte) 255, (byte) 128, (byte) 64, (byte) 32, (byte) 16, (byte) 8, (byte) 4,
                (byte) 2, (byte) 1};

        var bs = BitSet.valueOf(originalBytes);

        System.out.println(bs.get(0));
        System.out.println(bs.cardinality());

    }

    @Test
    void ByteTest() {
        var b = Byte.valueOf("32");

        System.out.println(Byte.toString(b));
        System.out.println(Byte.toUnsignedInt(b));
    }
}
