package com.example.sample.exam.security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;

import lombok.extern.slf4j.Slf4j;

/*
 * https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/security/package-summary.html
 */

// Digital Signing (RSA)
@Slf4j
public class DigitalSignatureTests {
    private static final String ALGORITHM = "RSA";
    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";

    void run() throws Exception {
        // 키 쌍 생성
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        // 서명 생성
        String data = "Hello, World!";
        byte[] signature = sign(data, privateKey);

        // Base64로 인코딩된 서명 출력
        String encodedSignature = Base64.getEncoder().encodeToString(signature);
        log.info("Signature: " + encodedSignature);

        // 서명 검증
        boolean isVerified = verify(data, signature, publicKey);
        log.info("Signature Verified: " + isVerified);
    }

    public static byte[] sign(String data, PrivateKey key) throws Exception {
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(key);
        signature.update(data.getBytes());
        return signature.sign();
    }

    public static boolean verify(String data, byte[] signature, PublicKey key) throws Exception {
        Signature sig = Signature.getInstance(SIGNATURE_ALGORITHM);
        sig.initVerify(key);
        sig.update(data.getBytes());
        return sig.verify(signature);
    }
}
