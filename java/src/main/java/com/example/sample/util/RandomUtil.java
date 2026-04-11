package com.example.sample.util;

import java.security.SecureRandom;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum RandomUtil {

    RandomUtil;

    private static final Logger log = LoggerFactory.getLogger(RandomUtil.class);

    private final Random random;

    private static final int TIME_CHECK = 3000;

    private RandomUtil() {
        random = new SecureRandom();
    }

    public static RandomUtil getInstance() {
        return RandomUtil;
    }

    /**
     * 
     * @param size random size
     * @return bytes
     */
    public byte[] getRandomBytes(int size) {
        byte[] bytes = new byte[size];
        long start = System.currentTimeMillis();
        // copy random bytes from random source(SecureRandom)
        random.nextBytes(bytes);
        long end = System.currentTimeMillis();
        if (end - start > TIME_CHECK) {
            log.warn("Random create Time : " + (end - start));
        }
        return bytes;
    }

    /**
     * 
     * @param bound the upper bound (exclusive). Must be positive.
     * @return random int between 0 (inclusive) and bound (exclusive)
     */
    public int getRandomInt(int bound) {
        long start = System.currentTimeMillis();
        int r = random.nextInt(bound);
        long end = System.currentTimeMillis();
        if (end - start > TIME_CHECK) {
            log.warn("Random create Time : " + (end - start));
        }
        return r;
    }
}