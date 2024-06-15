package com.example.sample.exam.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import lombok.extern.slf4j.Slf4j;

/*
 * https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/security/package-summary.html
 */

// Message Digest (SHA-256)
@Slf4j
public class MessageDigestTests {
    void run() throws NoSuchAlgorithmException {
        String input = "Hello, World!";
        String hash = hash(input);
        log.info("SHA-256 Hash: " + hash);
    }

    public static String hash(String input) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedHash = digest.digest(input.getBytes());
        return Base64.getEncoder().encodeToString(encodedHash);
    }
}
