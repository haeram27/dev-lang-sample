package com.example.sample.basic;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

public class TimerTests {

    @Test
    public void testTimerSchedule() throws InterruptedException {
        // 1. java.util.Timer (Simple, but single-threaded per Timer)
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Timer task executed at: " + new Date());
            }
        };

        // Execute after 2 seconds from now (simulating a specific time)
        // In real usage, you would pass a specific Date object
        Date specificTime = new Date(System.currentTimeMillis() + 3000);
        System.out.println("Task scheduled for: " + specificTime);
        
        timer.schedule(task, specificTime);

        // Keep the test alive to see the output
        Thread.sleep(5000);
        timer.cancel();
    }

    @Test
    public void testScheduledExecutorService() throws InterruptedException {
        // 2. ScheduledExecutorService (More robust, thread pools)
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Runnable task = () -> System.out.println("Executor task executed at: " + new Date());

        // Target: 2 seconds from now
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime targetTime = now.plusSeconds(2);

        System.out.println("Task scheduled for: " + targetTime);

        // Calculate delay
        long delay = java.time.Duration.between(now, targetTime).toMillis();

        scheduler.schedule(task, delay, TimeUnit.MILLISECONDS);

        // Keep the test alive
        Thread.sleep(3000);
        scheduler.shutdown();
    }
}