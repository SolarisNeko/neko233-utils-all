package com.neko233.common.threadPool;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class PrefixThreadFactory implements ThreadFactory {

    private final String prefix;
    private final AtomicInteger counter = new AtomicInteger(1);

    public PrefixThreadFactory(String prefix) {
        this.prefix = prefix;
    }

    public static PrefixThreadFactory create(String prefix) {
        return new PrefixThreadFactory(prefix);
    }

    @Override
    public Thread newThread(@NotNull Runnable r) {
        Thread t = new Thread(r);
        t.setName(prefix + "-" + counter.getAndIncrement());
        return t;
    }
}
