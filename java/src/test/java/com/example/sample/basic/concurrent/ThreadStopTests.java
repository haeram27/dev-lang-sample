package com.example.sample.basic.concurrent;

import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadStopTests {

    /**
     * How to stop thread in Java
     * - AtomicBoolean or volatile boolean
     * - Thread Interrupt
     */

    @Test
    public void threadTerminateByAtomicBoolean() {
        final AtomicBoolean running = new AtomicBoolean(true); // volatile Boolean also OK !

        log.info("[main] Start");
        Thread t = new Thread() {
            @Override
            public void run() {
                Thread.currentThread().setName("worker-1");
                var name = Thread.currentThread().getName();
                log.info("[{}] Start", name);

                int loop_count = 0;

                while (running.get()) { // check running flag
                    // do work
                    log.info("Running: " + ++loop_count);

                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        log.info("catch interrupt during sleep");
                    }
                }

                log.info("running: {}", running.get());
                log.info("[{}] End", name);
            }
        };
        t.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {}

        log.info("set running false");
        running.set(false);

        // (optional) wait for terminate work thread, if main thread is infinite then this is not required
        // Junit @Test(main) thread is terminate at once here, so it reqires join() for worker thread
        try {
            t.join();
        } catch (InterruptedException e) {
            log.info("[main] thread interrupted");
        }

        log.info("[main] End");
    }

    @Test
    public void threadTerminateByInterrupt() {
        log.info("[main] Start");
        Thread t = new Thread() {
            @Override
            public void run() {
                Thread.currentThread().setName("worker-1");
                var name = Thread.currentThread().getName();
                log.info("[{}] Start", name);

                int loop_count = 0;

                while (true) {
                    log.info("Running: " + ++loop_count);

                    /**
                     * WeakPoint:
                     * Do NOT catch interrupt before Thread.interrupted()
                     * Once catched interrupt will be released so it can NOT catched at next code
                     * This code restore interrupt again for the TEST
                     */
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        log.info("catch interrupt during sleep");

                        // restore interrupt for Thread.interrupted()
                        Thread.currentThread().interrupt();
                    }

                    // check interrupted
                    if (Thread.interrupted()) {
                        log.info("thread interrupted. terminate loop");
                        break;
                    }
                }

                log.info("[{}] End", name);
            }
        };
        t.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {}

        log.info("interrupt thread");
        t.interrupt();

        // (optional) wait for terminate work thread, if main thread is infinite then this is not required
        // Junit @Test(main) thread is terminate at once here, so it reqires join() for worker thread
        try {
            t.join();
        } catch (InterruptedException e) {
            log.info("[main] thread interrupted");
        }

        log.info("[main] End");
    }
}
