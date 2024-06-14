package com.example.sample.util;

import java.security.SecureRandom;
import java.util.Random;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum RandomUtil {

    RandomUtil;

    private final Random rnd;

    private static final int TIME_CHECK = 3000;

    private RandomUtil() {
        rnd = new SecureRandom();
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
        rnd.nextBytes(bytes);
        long end = System.currentTimeMillis();
        if (end - start > TIME_CHECK) {
            log.warn("Random create Time : " + (end - start));
        }
        return bytes;
    }

    /**
     * 
     * @param limit
     * @return
     */
    public int getRandomInt(int bound) {
        long start = System.currentTimeMillis();
        int random = rnd.nextInt(bound);
        long end = System.currentTimeMillis();
        if (end - start > TIME_CHECK) {
            log.warn("Random create Time : " + (end - start));
        }
        return random;
    }
}