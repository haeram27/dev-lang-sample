package com.example.sample.basic.concurrent;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadControlTests {

    @Test
    public void waitAndNotify() {
        final Object LOCAL_SYNC_OBJ = new Object();

        Thread thd1 = new Thread() {
            @Override
            public void run() {
                Thread.currentThread().setName("Test-Thread-1");
                var name = Thread.currentThread().getName();

                log.info("[{}] thread wait", name);
                synchronized (LOCAL_SYNC_OBJ) { // get owner of LOCAL_SYNC_OBJ
                    try {
                        LOCAL_SYNC_OBJ.wait();
                    } catch (InterruptedException e) {
                        log.info("[{}] thread interrupted 1", name);
                    }
                }

                for (int i = 0; i <= 20; i++) {
                    log.info("[{}] {}", name, i);
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        log.info("[{}] thread interrupted 2", name);
                    }
                }

                log.info("[{}] terminate", name);
            }
        };

        Thread thd2 = new Thread() {
            @Override
            public void run() {
                Thread.currentThread().setName("Test-Thread-2");
                var name = Thread.currentThread().getName();

                log.info("[{}] thread wait", name);
                synchronized (LOCAL_SYNC_OBJ) { // get owner of LOCAL_SYNC_OBJ
                    try {
                        LOCAL_SYNC_OBJ.wait();
                    } catch (InterruptedException e) {
                        log.info("[{}] thread interrupted 1", name);
                    }
                }

                for (int i = 0; i <= 20; i++) {
                    log.info("[{}] {}", name, i);
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        log.info("[{}] thread interrupted 2", name);
                    }
                }

                log.info("[{}] terminate", name);
            }
        };

        log.info("Thread Start");
        thd1.start();
        thd2.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.info("[main] thread interrupted");
        }

        synchronized (LOCAL_SYNC_OBJ) {
            log.info("Thread notify All");
            LOCAL_SYNC_OBJ.notifyAll();
        }

        try {
            thd1.join();
            thd2.join();
        } catch (InterruptedException e) {
            log.info("[main] thread interrupted");
        }
    }

    @Test
    public void terminateByInterrupt() {
        Thread t = new Thread() {
            @Override
            public void run() {
                int loop_count = 0;
                while (true) {
                    log.info("Running: " + ++loop_count);
                    // try {
                    //     Thread.sleep(500);
                    // } catch (InterruptedException e) {
                    //     log.info("thread interrupted during sleep");
                    // }

                    // check interrupted
                    if (Thread.interrupted()) {
                        log.info("thread interrupted. terminate loop");
                        break;
                    }
                }
                log.info("retrieving resources");
                log.info("thread terminated");
            }
        };

        log.info("interrupt thread");
        t.start();

        /**
         * Thread Class의 run() 메소드 내에서 sleep()호출이 아닌 일반 메소드 내에서 Runtime Thread에 대해
         * sleep을 호출 해야 할 경우 Thread.sleep() - Thread Class의 static sleep() 메소드를 호출하여야 한다.
         */
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {}
        log.info("interrupt thread");
        t.interrupt();

        try {
            t.join();
        } catch (InterruptedException e) {
        }
    }
}
