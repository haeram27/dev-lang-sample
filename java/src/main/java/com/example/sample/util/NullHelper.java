package com.example.sample.util;

import java.math.BigDecimal;
import java.math.BigInteger;

public class NullHelper {
    static BigDecimal nullToZero(BigDecimal n) {
        return (n == null) ? BigDecimal.ZERO : n;
    }

    static BigInteger nullToZero(BigInteger n) {
        return (n == null) ? BigInteger.ZERO : n;
    }

}
