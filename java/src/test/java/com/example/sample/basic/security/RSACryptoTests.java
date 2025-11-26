package com.example.sample.basic.security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import javax.crypto.Cipher;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

/*
 * https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/security/package-summary.html
 */

@Slf4j
public class RSACryptoTests {
    private static final String ALGORITHM = "RSA";

    @Test
    void run() throws Exception {
        // 키 쌍 생성
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        // 암호화
        String plaintext = "Hello, World!";
        byte[] ciphertext = encrypt(plaintext, publicKey);

        // Base64로 인코딩된 암호문 출력
        String encodedCiphertext = Base64.getEncoder().encodeToString(ciphertext);
        log.info("Encrypted: " + encodedCiphertext);

        // 복호화
        String decryptedText = decrypt(ciphertext, privateKey);
        log.info("Decrypted: " + decryptedText);
    }

    public static byte[] encrypt(String plaintext, PublicKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(plaintext.getBytes());
    }

    public static String decrypt(byte[] ciphertext, PrivateKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] plaintext = cipher.doFinal(ciphertext);
        return new String(plaintext);
    }
}
