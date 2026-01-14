package com.example.sample.basic;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileIOTests {
    private static final String TEST_FILE_PATH = "/tmp/test-upload";
    private static final int CHUNK_SIZE = 1024 * 1024 * 100; // 100MB Chunk
    private static final AtomicBoolean running = new AtomicBoolean(true);
    private Thread fileWriteThread;

    @Test
    public void fileWriteStart() {
        running.set(true);

        fileWriteThread = new Thread(new Runnable() {
            @Override
            public void run() {
                var tname = "TestFileWrite-Thread";
                Thread.currentThread().setName(tname);
                log.info("[{}] Thread Started", tname);

                // 테스트용 더미 데이터 생성 (매번 생성하면 CPU 낭비가 심하므로 재사용)
                byte[] dummyData = new byte[CHUNK_SIZE];
                new Random().nextBytes(dummyData);

                // 디렉토리 생성
                new File(TEST_FILE_PATH).mkdirs();

                long totalBytesWritten = 0;
                int count = 0;

                while (running.get()) {
                    try {
                        count++;
                        String fileName = UUID.randomUUID().toString() + ".temp";
                        long startTime = System.currentTimeMillis();

                        // 파일 쓰기 수행
                        writeTestFileRaw(fileName, dummyData);

                        long duration = System.currentTimeMillis() - startTime;
                        totalBytesWritten += CHUNK_SIZE;

                        log.info("Chunk #{} Written. File: {}, Size: 10MB, Time: {}ms, Total: {}MB", 
                            count, fileName, duration, totalBytesWritten / (1024 * 1024));

                        } catch (Exception e) {
                        log.error("## File Write Error", e);
                        try { Thread.sleep(1000); } catch (InterruptedException ie) {}
                    }
                }

                log.info("[{}] Thread Terminated", tname);
            }
        });
        fileWriteThread.start();
    }

    public void fileWriteStop() {
        if (fileWriteThread != null && fileWriteThread.isAlive()) {
            log.info("Request TestFileWrite Thread Terminate");
            running.set(false);
            fileWriteThread = null;
        }
    }

    private void writeTestFile(String fileName, byte[] sourceData) throws IOException {
        File targetFile = new File(TEST_FILE_PATH, fileName);

        // 1. Input Stream 준비 (메모리 데이터를 스트림으로 변환)
        InputStream is = new ByteArrayInputStream(sourceData);

        try (
            // 2. RandomAccessFile & FileChannel 열기
            RandomAccessFile raf = new RandomAccessFile(targetFile, "rw");
            FileChannel fileChannel = raf.getChannel();

            // 3. InputStream을 ReadableByteChannel로 변환
            ReadableByteChannel inputChannel = Channels.newChannel(is)
        ) {
            // Direct Buffer 할당 (Native I/O 성능 최적화)
            ByteBuffer buffer = ByteBuffer.allocateDirect(64 * 1024); // 64 KB Buffer

            // 4. Channel 간 데이터 전송
            while (inputChannel.read(buffer) != -1) {
                buffer.flip(); // 읽기 모드 -> 쓰기 모드
                while (buffer.hasRemaining()) {
                    fileChannel.write(buffer);
                }
                buffer.clear(); // 버퍼 비우기
            }

            // 5. 디스크 강제 동기화 (Page Cache Flush 유도)
            fileChannel.force(true);
        }
    }

    private void writeTestFileRaw(String fileName, byte[] sourceData) throws IOException {
        File targetFile = new File(TEST_FILE_PATH, fileName);

        try (RandomAccessFile raf = new RandomAccessFile(targetFile, "rw")) {
            raf.write(sourceData);
            raf.getFD().sync();
        }
    }
}
