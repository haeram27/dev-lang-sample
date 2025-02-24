package com.example.sample.exam.concurrent;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.Test;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CompletableFutureTests {
    
    @Test
    public void supplyAsyncTest() {
        ExecutorService es = Executors.newSingleThreadExecutor();
        try {
                CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                    var now = OffsetDateTime.ofInstant(Instant.now(),
                    ZoneId.systemDefault()).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

                    log.trace("{}, {}}", Thread.currentThread().getName(), now);

                    try {
                        Thread.sleep(1000); // task delay
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }

                    return "Result from: " + Thread.currentThread().getName();
                }, es);

            // get result
            String result = future.join();
            log.trace("Completed with result: " + result);
        } finally {
            // ExecutorService shutdown
            es.shutdown();
        }
    }


    @Test
    public void supplyAsycPararrelTest() {
        ExecutorService es = Executors.newFixedThreadPool(5);

        try {
                CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                    var now = OffsetDateTime.ofInstant(Instant.now(),
                    ZoneId.systemDefault()).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

                    log.trace("{}, {}}", Thread.currentThread().getName(), now);

                    try {
                        Thread.sleep(1000); // task delay
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }

                    return "Result from: " + Thread.currentThread().getName();
                }, es);

            // get result (wait for completion async)
            String result = future.join();
            log.trace("Completed with result: " + result);
        } finally {
            // ExecutorService shutdown
            es.shutdown();
        }
    }

    @Test
    public void concurrentAndPararrel() {
        // 작업을 담을 큐 생성
        Queue<Integer> taskQueue = new LinkedList<>();
        for (int i = 1; i <= 20; i++) {
            taskQueue.add(i); // 20개의 작업 추가
        }

        // 사용자 정의 스레드 풀 생성
        ExecutorService executorService = Executors.newFixedThreadPool(5); // 5개의 스레드
        // 작업을 병렬 처리
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        for (int i = 0; i < 5; i++) { // 5개의 워커 스레드
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                while (!taskQueue.isEmpty()) {
                    Integer task;
                    synchronized (taskQueue) { // 동기화하여 안전하게 작업 꺼냄
                        task = taskQueue.poll();
                    }
                    if (task != null) {
                        processTask(task);
                    }
                }
            }, executorService);
            futures.add(future);
        }

        // 모든 작업 완료 대기
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        // 스레드 풀 종료
        executorService.shutdown();
        System.out.println("All tasks completed.");
    }

    // 작업 처리 메서드
    private static void processTask(Integer task) {
        System.out.println("Processing task: " + task + " by " + Thread.currentThread().getName());
        try {
            Thread.sleep(500); // 작업 처리 시간 시뮬레이션
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
