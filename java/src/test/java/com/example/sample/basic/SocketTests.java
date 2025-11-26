package com.example.sample.basic;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.junit.jupiter.api.Test;

import com.example.sample.EvaluatedTimeTests;
import com.example.sample.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SocketTests extends EvaluatedTimeTests {

    public enum ProtocolType {
        UDP,
        TCP,
        TCP_OVER_SSL
    }

    public boolean validateSocketConnection(String host, int port, ProtocolType protocol) {

        if (StringUtil.isEmpty(host) || host.isBlank())
            return false;
        if (port == 0)
            return false;
        if (protocol == null)
            return false;

        if (ProtocolType.TCP == protocol) {
            try (Socket socket = createTCPSocket(host, port, false, 2000, 2000);
                    PrintWriter out = new PrintWriter(new BufferedWriter(
                            new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8)), true)) {
                out.println("connection test");
                out.flush();
            } catch (Exception e) {
                log.error("## TCP Socket connection error:", e);
                return false;
            }
        } else if (ProtocolType.TCP_OVER_SSL == protocol) {
            try (SSLSocket socket = createSSLSocket(host, port, false, 2000, 2000);
                    PrintWriter out = new PrintWriter(new BufferedWriter(
                            new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8)), true)) {
                out.println("connection test");
                out.flush();
            } catch (Exception e) {
                log.error("## TLS Socket connection error:", e);
                return false;
            }
        }

        return true;
    }

    private static Socket createTCPSocket(String host, int port, boolean keepAlive, int connectionTimeout, int readTimeout)
            throws Exception {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(host, port), connectionTimeout);
        socket.setSoTimeout(readTimeout);
        socket.setKeepAlive(keepAlive);

        return socket;
    }

    private static SSLSocket createSSLSocket(String host, int port, boolean keepAlive, int connectionTimeout, int readTimeout)
            throws Exception {
        TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {
                // do nothing
            }

            @Override
            public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {
                // do nothing
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }};

        SSLContext sc = SSLContext.getInstance("TLS");
        sc.init(null, trustAllCerts, new SecureRandom());

        SSLSocket socket = (SSLSocket) sc.getSocketFactory().createSocket();
        socket.connect(new InetSocketAddress(host, port), connectionTimeout);
        socket.setSoTimeout(readTimeout);
        socket.setKeepAlive(keepAlive);

        return socket;
    }

    @Test
    public void tcpSocketConnTest() {
        log.info("{}", validateSocketConnection("10.101.10.1", 514, ProtocolType.TCP));
    }

    @Test
    public void tlsSocketConnTest() {
        log.info("{}", validateSocketConnection("10.101.10.1", 6514, ProtocolType.TCP_OVER_SSL));        
    }
}
