package com.example.sample.exam;

import org.junit.jupiter.api.Test;

public class EnumTests {
    enum Number {
        ONE(0x01),
        TWO(0x02),
        THREE(0x03);

        private final int code;

        private Number(final int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    @Test
    void printEnums() {
        System.out.println("toString(): " + Number.ONE.toString());
        System.out.println("name(): " + Number.ONE.name());
        System.out.println("ordinal(): " + Number.ONE.ordinal());
        System.out.println("getCode(): " + Number.ONE.getCode());

        System.out.println("toString(): " + Number.TWO.toString());
        System.out.println("name(): " + Number.TWO.name());
        System.out.println("ordinal(): " + Number.TWO.ordinal());
        System.out.println("getCode(): " + Number.TWO.getCode());

        System.out.println("toString(): " + Number.THREE.toString());
        System.out.println("name(): " + Number.THREE.name());
        System.out.println("ordinal(): " + Number.THREE.ordinal());
        System.out.println("getCode(): " + Number.THREE.getCode());
    }
}
