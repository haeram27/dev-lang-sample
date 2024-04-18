package com.example.sample.base.bitwise;

import java.util.BitSet;
import org.junit.jupiter.api.Test;

public class BitSetTests {
    @Test
    void BitsetTest() {

        byte[] originalBytes2 = new byte[] {(byte) 1, (byte) 1};
        byte[] originalBytes9 = new byte[] {(byte) 255, (byte) 128, (byte) 64, (byte) 32, (byte) 16, (byte) 8, (byte) 4,
                (byte) 2, (byte) 1};

        var bs2 = BitSet.valueOf(originalBytes2);
        System.out.println(bs2.get(0));
        System.out.println(bs2.cardinality());

        var bs9 = BitSet.valueOf(originalBytes9);
        System.out.println(bs9.get(0));
        System.out.println(bs9.cardinality());
    }

    @Test
    void ByteTest() {
        var b = Byte.valueOf("32");

        System.out.println(Byte.toString(b));
        System.out.println(Byte.toUnsignedInt(b));
    }
}
