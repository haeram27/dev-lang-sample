package com.example.sample.basic.concurrent;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadLockTests {
    /**
     * Lock Objects for synchroniaztion in Java
     *  - Object (instrinsic lock or monitor lock) : supports wait, notify, notifyAll
     *  - Lock : suports lock, trylock, unlock
     *    - ReadWriteLock
     *    - ReentrantLock
     *    - ReentrantReadWriteLock
     *    - Condition : supports await, signal for Lock instance
     */

    @Test
    public void objectMonitorLock() {
        /**
         * Intrinsic Locks and Synchronization - https://docs.oracle.com/javase/tutorial/essential/concurrency/locksync.html
         * How to get ownership of Object Monitor
         *   - https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/lang/Object.html#notify()
         *   By executing a synchronized instance method of that object.
         *   By executing the body of a synchronized statement that synchronizes on the object.
         *   For objects of type Class, by executing a static synchronized method of that class.
         */

        // LOCAL_SYNC_OBJ (monitor)
        final Object LOCAL_SYNC_OBJ = new Object();

        log.info("[main] Start");

        // Thread 1
        Thread t1 = new Thread() {
            @Override
            public void run() {
                Thread.currentThread().setName("worker-1");
                var name = Thread.currentThread().getName();
                log.info("[{}] Start", name);

                synchronized (LOCAL_SYNC_OBJ) { // get ownership of LOCAL_SYNC_OBJ
                    try {
                        log.info("[{}] thread wait", name);
                        LOCAL_SYNC_OBJ.wait();
                    } catch (InterruptedException e) {
                        log.info("[{}] thread interrupted", name);
                    }
                }

                for (int i = 0; i <= 20; i++) {
                    log.info("[{}] {}", name, i);
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        log.info("[{}] thread interrupted", name);
                    }
                }

                log.info("[{}] thread terminate", name);
            }
        };

        // Thread 2
        Thread t2 = new Thread() {
            @Override
            public void run() {
                Thread.currentThread().setName("worker-2");
                var name = Thread.currentThread().getName();
                log.info("[{}] Start", name);

                synchronized (LOCAL_SYNC_OBJ) { // get ownership of LOCAL_SYNC_OBJ
                    try {
                        log.info("[{}] thread wait", name);
                        LOCAL_SYNC_OBJ.wait();
                    } catch (InterruptedException e) {
                        log.info("[{}] thread interrupted", name);
                    }
                }

                for (int i = 0; i <= 20; i++) {
                    log.info("[{}] {}", name, i);
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        log.info("[{}] thread interrupted", name);
                    }
                }

                log.info("[{}] thread terminate", name);
            }
        };

        t1.start();
        t2.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.info("[main] thread interrupted");
        }

        synchronized (LOCAL_SYNC_OBJ) {  // get ownership of LOCAL_SYNC_OBJ
            log.info("Thread notify All");
            LOCAL_SYNC_OBJ.notifyAll();
        }

        // (optional) wait for terminate work thread, if main thread is infinite then this is not required
        // Junit @Test(main) thread is terminate at once here, so it reqires join() for worker thread
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            log.info("[main] thread interrupted");
        }

        log.info("[main] End");
    }
}
