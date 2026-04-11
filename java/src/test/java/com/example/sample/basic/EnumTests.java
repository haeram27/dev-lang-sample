package com.example.sample.basic;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnumTests {

    private static final Logger log = LoggerFactory.getLogger(EnumTests.class);

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

        public static boolean isValidEnumName(String s) {
            if (s == null) return false;
            try {
                Number.valueOf(s); // return a Number n
                return true;
            } catch (IllegalArgumentException e) {
                return false;
            }
        }
    }

    @Test
    void printEnums() {
        for (var n : Number.values()) {
            log.info("=== " + n.toString() + " ==============");
            log.info("toString(): " + n.toString());
            log.info("name(): " + n.name());        // enum item name
            log.info("ordinal(): " + n.ordinal());  // enum item order number from 0
            log.info("getCode(): " + n.getCode());  // item's user defined internal value
        }
    }

    @Test
    void enumSwitchPatternTest() {
        var item = "TWO";
        switch(Number.valueOf(item)) {
            case Number.ONE:
                log.info("=== ONE ==============");
                log.info("toString(): " + Number.ONE.toString());
                log.info("name(): " + Number.ONE.name());
                log.info("ordinal(): " + Number.ONE.ordinal());
                log.info("getCode(): " + Number.ONE.getCode());
                break;
            case Number.TWO:
                log.info("=== TWO ==============");
                log.info("toString(): " + Number.TWO.toString());
                log.info("name(): " + Number.TWO.name());
                log.info("ordinal(): " + Number.TWO.ordinal());
                log.info("getCode(): " + Number.TWO.getCode());
                break;
            case Number.THREE:
                log.info("=== THREE ==============");
                log.info("toString(): " + Number.THREE.toString());
                log.info("name(): " + Number.THREE.name());
                log.info("ordinal(): " + Number.THREE.ordinal());
                log.info("getCode(): " + Number.THREE.getCode());
                break;
            default:
                break;
        }
    }

    @Test
    void validNameTest() {
        log.info("{}", Number.isValidEnumName("ONE")); // true
        log.info("{}", Number.isValidEnumName("TEN")); // false
    }
}
