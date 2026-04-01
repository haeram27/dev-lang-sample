package com.example.sample.basic.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VirtualThreadTests {
    /**
     * Since Java 21 - virtual thread added
     * ThreadPool is NOT required for virtual thread
     * virtual thread can be executed with unbounded number vthread instance regardless of physical thread cost
     */

    private AtomicInteger count = new AtomicInteger();

    @Test
    public void singleVirtualThreadTest() {
        try {
            // Thread.startVirtualThread()
            var t = Thread.ofVirtual().start(() -> {
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
                log.info("ofVirtual: " + count.incrementAndGet());
            });
            t.join();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Test
    public void virtualThreadExecutorTest() {
        var taskSize = 300;
        var list = new ArrayList<Integer>();
        for (int i=0; i<taskSize; i++) list.add(i);

        // Executors.newVirtualThreadPerTaskExecutor() makes unbounded size thread
        // ExecutorService SHOULD be called close()
        try (var es = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<Integer>> futures = list.stream()
                .map(i -> es.submit(() -> {
                    try {
                        log.info("val: "+ i);
                        // Callable lambda throw all exceptions to Future
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                    return i;
                }))
                .toList();

            // use CountDownLatch to wait for all thread completion, but Future.get() is more convenient to wait for completion and get result
            boolean allDone = futures.stream().allMatch(Future::isDone);
            log.info("alldone: " + allDone); // alldone: false here

            for (var f : futures) {
                // Each Future.get() block invoking current thread
                log.info("result: " + f.get(2, java.util.concurrent.TimeUnit.SECONDS));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
