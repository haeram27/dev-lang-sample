package com.example.sample.basic.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Base64;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

/*
 * https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/security/package-summary.html
 */

// Message Digest (SHA-256)
@Slf4j
public class MessageDigestTests {

    @Test
    void run() throws NoSuchAlgorithmException {
        String input = "Hello, World!";
        String hash = hash(input);
        System.out.println("SHA-256 Hash: " + hash);
    }

    @Test
    void printSecurityProviders() {
        var providers = Security.getProviders();
        System.out.println(providers.length);
        if (providers.length > 0) {
            for (var provider : providers) {
                System.out.println(provider.getName());
            }
        }
    }

    public static String hash(String input) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedHash = digest.digest(input.getBytes());
        return Base64.getEncoder().encodeToString(encodedHash);
    }

    public static String hashWithSalt(String input, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(salt);
        byte[] encodedHash = digest.digest(input.getBytes());
        return Base64.getEncoder().encodeToString(encodedHash);
    }
    
    public static byte[] getSecureRandomSalt() {
        var saltString = Integer.valueOf(new SecureRandom().nextInt()).toString();
        log.info("salt: " + saltString);
        return saltString.getBytes();
    }
}
