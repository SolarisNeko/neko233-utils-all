package com.neko233.common.cache.ref;

import org.jetbrains.annotations.Nullable;

import java.lang.ref.WeakReference;

/**
 * @author LuoHaoJun on 2022-12-12
 **/
public class TimeWeakRef<V> extends WeakReference<V> {

    private volatile long visitMs;

    public TimeWeakRef(V data) {
        super(data);
        this.visitMs = System.currentTimeMillis();
    }

    public long getVisitMs() {
        return visitMs;
    }

    private TimeWeakRef<V> refreshTime() {
        visitMs = System.currentTimeMillis();
        return this;
    }

    @Nullable
    @Override
    public V get() {
        refreshTime();
        return super.get();
    }
}
