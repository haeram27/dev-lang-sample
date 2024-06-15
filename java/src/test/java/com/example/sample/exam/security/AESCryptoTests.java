package com.example.sample.exam.security;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

import org.junit.jupiter.api.Test;

import com.example.sample.util.RandomUtil;

import lombok.extern.slf4j.Slf4j;

/*
 * https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/security/package-summary.html
 */
@Slf4j
public class AESCryptoTests {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int GCM_TAG_LENGTH = 16;

    @Test
    void run() throws Exception {
        // 키 생성
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        keyGenerator.init(256);
        SecretKey secretKey = keyGenerator.generateKey();

        // 암호화
        String plaintext = "Hello, World!";
        byte[] iv = RandomUtil.getInstance().getRandomBytes(12);
        log.info("iv: {}", iv);
        byte[] ciphertext = encrypt(plaintext, secretKey, iv);

        // Base64로 인코딩된 암호문 출력
        String encodedCiphertext = Base64.getEncoder().encodeToString(ciphertext);
        log.info("Encrypted: " + encodedCiphertext);

        // 복호화
        String decryptedText = decrypt(ciphertext, secretKey, iv);
        log.info("Decrypted: " + decryptedText);
    }

    public static byte[] encrypt(String plaintext, SecretKey key, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, spec);
        return cipher.doFinal(plaintext.getBytes());
    }

    public static String decrypt(byte[] ciphertext, SecretKey key, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, iv);
        cipher.init(Cipher.DECRYPT_MODE, key, spec);
        byte[] plaintext = cipher.doFinal(ciphertext);
        return new String(plaintext);
    }
}
