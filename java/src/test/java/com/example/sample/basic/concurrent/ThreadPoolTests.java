package com.example.sample.basic.concurrent;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
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

    private static AtomicInteger taskId = new AtomicInteger();
    private static AtomicInteger threadId = new AtomicInteger();

    /**
     *  Good: corePoolSize should be greater than 0 to make new thread when all threads are busy and queue is full, and allowCoreThreadTimeOut(true) should be called to allow core thread to be terminated when it is idle, so it is suitable for CPU intensive tasks but not suitable for IO intensive tasks because of small queue size
     */
    public static ExecutorService customGoodUnboundedCachedThreadPool() {
        // @formatter:off
        int halfNumberOfThreads = Runtime.getRuntime().availableProcessors()/2;
        int maxNumberOfThreads = Runtime.getRuntime().availableProcessors();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                    // fixed thread pool size
                    halfNumberOfThreads, // corePoolSize : usually executor keeps corePoolSize threads alive
                    maxNumberOfThreads, // maximumPoolSize : new thread is created when only all threads are busy and queue is full, if queue is unbounded then new thread will NEVER be created (ignored)
                    3L, TimeUnit.SECONDS, // keep alive duration in idle status, 60 sec recommended
                    new LinkedBlockingDeque<>(), // blocking queue
                    r -> {
                        Thread t = new Thread(r);
                        t.setName("executor-worker-" + threadId.accumulateAndGet(Integer.MAX_VALUE, (current, max) -> 
                            current >= max ? 0 : current + 1
                        ));
                        // t.setDaemon(true);
                        return t;
                    });
        // allow core thread to be terminated when it is idle, default is false and core thread is never terminated even if it is idle
        executor.allowCoreThreadTimeOut(true);
        return executor;
        // @formatter:on
    }

    /**
     *  Wrong : This ThreadPoolExecutor always uses only 1 (minimum) thread and all tasks are added to queue without blocking, so it is not suitable for CPU intensive tasks but suitable for IO intensive tasks
     */
    public static ExecutorService customWrongUnboundedCachedThreadPool() {
        // @formatter:off
        int corePoolSize = Runtime.getRuntime().availableProcessors()/2;
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                    // thread pool size range, new thread is created when only all threads are busy and queue is full
                    // if queue is unbounded then new thread will not be created because all tasks are added to queue without blocking
                    // so this ThreadPoolExecutor uses only 1 thread and all tasks are added to queue without blocking, so it is not suitable for CPU intensive tasks but suitable for IO intensive tasks
                    0, corePoolSize, // only 1 thread is created if corePoolSize is 0
                    60L, TimeUnit.SECONDS, // keep alive duration in idle status, 60 sec recommended
                    new LinkedBlockingDeque<>(), // blocking queue
                    r -> {
                        Thread t = new Thread(r);
                        t.setName("executor-worker-" + threadId.accumulateAndGet(Integer.MAX_VALUE, (current, max) -> 
                            current >= max ? 0 : current + 1
                        ));
                        // t.setDaemon(true);
                        return t;
                    });
        executor.allowCoreThreadTimeOut(true); // allow core thread to be terminated when idle
        return executor;
    }

    /**
     *  Wrong : This ThreadPoolExecutor can be throw RejectedExecutionException when queue is full and busy threads are reached to maxPoolSize, so it is not suitable for CPU intensive tasks but suitable for IO intensive tasks
     */
    public static ExecutorService customWrongBoundedCachedThreadPool() {
        // @formatter:off

        // if queue is full and busy threads are reached to maxPoolSize then ThreadPoolExecutor throws RejectedExecutionException by default handler (AbortPolicy)
        int corePoolSize = Runtime.getRuntime().availableProcessors()/2;
        return new ThreadPoolExecutor(
                    // thread pool size range, new thread is created when only all threads are busy and queue is full
                    // if queue is unbounded then new thread will not be created because all tasks are added to queue without blocking
                    0, corePoolSize,
                    60L, TimeUnit.SECONDS, // keep alive duration in idle status, 60 sec recommended
                    new ArrayBlockingQueue<>(10), // blocking queue
                    r -> {
                        Thread t = new Thread(r);
                        t.setName("executor-worker-" + threadId.accumulateAndGet(Integer.MAX_VALUE, (current, max) -> 
                            current >= max ? 0 : current + 1
                        ));
                        // t.setDaemon(true);
                        return t;
                    });
        // @formatter:on
    }

    Callable<String> task = () -> {
        long oneHundredMicroSeconds = 100_000;
        long startedAt = System.nanoTime();

        var tid = taskId.accumulateAndGet(Integer.MAX_VALUE, (current, max) -> current >= max ? 0 : current + 1);
        var threadId = Thread.currentThread().getName();
        System.out.println("task-" + tid + "@" + threadId + " start");

        while (System.nanoTime() - startedAt <= oneHundredMicroSeconds) {
            Thread.sleep(100, 10_000);
        }

        return tid + " task Done";
    };

    @Test
    void cachedThreadPoolTest() {

        Collection<Callable<String>> tasks = IntStream.rangeClosed(1, 1000).mapToObj(i -> task).toList();

        // ExecutorService predefinedCachedThreadPool = Executors.newCachedThreadPool();
        ExecutorService es = customGoodUnboundedCachedThreadPool();
        try {
            List<Future<String>> result = es.invokeAll(tasks);
            for (Future<String> e : result) {
                System.out.println(e.get());
            }

            if (es instanceof ThreadPoolExecutor) {
                Thread.sleep(4000); // wait for all tasks are completed and threads are idle
                System.out.println("Current pool size: " + ((ThreadPoolExecutor) es).getPoolSize());
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
