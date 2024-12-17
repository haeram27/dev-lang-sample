package com.example.sample.exam.concurrent;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

public class ThreadPoolTests {

    /*
        https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/concurrent/package-summary.html
    
        # java.util.concurrent Queues
        BlockingQueue? When queue is empty, queue make consumer Thread as waiting status.
        Non-BlockingQueue(ConcurrentLinkedQueue)? When queue is empty, queue returns null instead making consumer thread as waiting status.
    
        // Blocking queue    
        ArrayBlockingQueue(M): blocking, bounded queue. size shoud be initialized
        LinkedBlockingQueue(M): blocking, selectively unbounded queue. size is NOT limited by using list arch
        SynchronousQueue: blocking, 0 size queue. each insertion operation should wait remove operation of consumer thread.
        PriorityBlockingQueue: blocking,
        DelayQueue: blocking, make a given delay before take() each element
    
        // Non-Blocking queue
        ConcurrentLinkedQueue(M): non-blocking, unbounded
        ConcurrentLinkedDeque: non-blocking, unbounded
    
        # sec
        1 (sec) = 1_000 (milli-sec) = 1_000_000 (micro-sec) = 1_000_000_000 (nano-sec)
    */

    public static ExecutorService customCachedThreadPool() {
        // @formatter:off
        return new ThreadPoolExecutor(
            0,
            Runtime.getRuntime().availableProcessors() / 2,
            60L,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>()
        );
        // @formatter:on
    }

    Callable<String> task = () -> {
        long oneHundredMicroSeconds = 100_000;
        long startedAt = System.nanoTime();
        while (System.nanoTime() - startedAt <= oneHundredMicroSeconds) {
            Thread.sleep(0, 10_000);
        }

        return "Done";
    };

    @Test
    void cachedThreadPoolTest() {

        Collection<Callable<String>> tasks = IntStream.rangeClosed(1, 20).mapToObj(i -> task).toList();

        // ExecutorService predefinedCachedThreadPool = Executors.newCachedThreadPool();
        ExecutorService cachedPool = customCachedThreadPool();
        try {
            List<Future<String>> result = cachedPool.invokeAll(tasks);
            for (Future<String> e : result) {
                System.out.println(e.get());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /* 
        ArrayBlockingQueue: restricts nubmer of tasks
        if offer() is failed by queue capacities are reached max
        then handler is called
        ThreadPoolExecutor's default handler is AbortPolicy class (RejectedExecutionHandler)
        and it throws RejectedExecutionException
    */
    @Test
    void threadPoolExecutorWithArrayBlockingQueue() {

        // max queue size is 100
        var boundedQueue = new ArrayBlockingQueue<Runnable>(100);
        // create 1000 tasks to blocking queue
        Collection<Callable<String>> tasks = IntStream.rangeClosed(1, 2_000).mapToObj(i -> task).toList();

        var cachedPool = new ThreadPoolExecutor(2, 20, 60, TimeUnit.SECONDS, boundedQueue);
        try {
            List<Future<String>> result = cachedPool.invokeAll(tasks);
            for (Future<String> e : result) {
                System.out.println(e.get());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /*
     * ForkJoinPool uses divide and conquer strategy
     * ForkJoinPool's tasks are made by ForkJoinTask and
     * it's implementations are RecursiveAction(Runnable), RecursiveTask(Callable), CountedCompleter
     */
    class ArraySumCalculator extends RecursiveTask<Long> {
        private static final int THRESHOLD = 100; // threshhold to do sequentialSum()
        private final int[] array;
        private final int start;
        private final int end;

        public ArraySumCalculator(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            if (end - start <= THRESHOLD) {
                // if task is as small as enough then make each task's result
                return sequentialSum();
            } else {
                // divide tasks as smaller
                int mid = (start + end) / 2; // (start + end) >>> 1
                ArraySumCalculator leftTask = new ArraySumCalculator(array, start, mid);
                ArraySumCalculator rightTask = new ArraySumCalculator(array, mid, end);

                // fork left task with another thread
                leftTask.fork();

                // compute right task
                long rightSum = rightTask.compute();

                // join left task's result
                long leftSum = leftTask.join();

                // make sum of two tasks
                return leftSum + rightSum;
            }
        }

        private long sequentialSum() {
            long sum = 0;
            for (int i = start; i < end; i++) {
                sum += array[i];
            }
            return sum;
        }
    }

    @Test
    void forkJoinPoolTest() {

        // int[] array = IntStream.range(0, 1000).toArray();
        // int[] array = IntStream.rangeClosed(0, 999).toArray();
        int[] array = new int[1000];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }

        ForkJoinPool forkJoinPool = new ForkJoinPool();

        ArraySumCalculator calculator = new ArraySumCalculator(array, 0, array.length);
        long sum = forkJoinPool.invoke(calculator);

        System.out.println("The sum is: " + sum);
    }
}
