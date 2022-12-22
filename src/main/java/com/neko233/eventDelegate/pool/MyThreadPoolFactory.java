package com.neko233.eventDelegate.pool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author SolarisNeko
 * Date on 2022-10-30
 */
public class MyThreadPoolFactory {


    public static ThreadPoolExecutor getThreadPoolOrDefault(String prefixName) {
        return defaultThreadPool(prefixName);
    }


    private static ThreadPoolExecutor defaultThreadPool(String prefixName) {
        return new ThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors(),
                Runtime.getRuntime().availableProcessors(),
                30,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(Runtime.getRuntime().availableProcessors() * 1000),
                new ThreadFactory() {
                    private final AtomicInteger counter = new AtomicInteger(1);

                    @Override
                    public Thread newThread(Runnable r) {
                        if (r == null) {
                            throw new NullPointerException();
                        }
                        return new Thread(r, prefixName + "-" + counter.getAndIncrement());
                    }
                },
                new ThreadPoolExecutor.AbortPolicy()
        );
    }
}
