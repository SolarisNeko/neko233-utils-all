package com.neko233.eventDelegate.delegate;


import com.neko233.eventDelegate.exception.ForceTransformDataException;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 实现 Delegate
 *
 * @author SolarisNeko
 * Date on 2022-10-30
 */
@Slf4j
public abstract class AbstractDelegateManager<T> {


    /**
     * 观察者
     */
    private final Set<EventObserver<T>> eventObserverSet = new LinkedHashSet<>();


    /**
     * 新增一个observer
     *
     * @param eventObserver 添加的观察者
     * @return 是否添加成功/新添加
     */
    public boolean addObserver(EventObserver<?> eventObserver) throws ForceTransformDataException {
        if (eventObserver == null) {
            throw new NullPointerException();
        }
        if (eventObserverSet.contains(eventObserver)) {
            // 重复添加, 不操作
            return false;
        }
        EventObserver<T> forceTransform = forceTransformObserver((EventObserver<T>) eventObserver);
        eventObserverSet.add(forceTransform);
        return true;
    }

    /**
     * 强制转换
     *
     * @param eventObserver
     * @return
     */
    private EventObserver<T> forceTransformObserver(EventObserver<T> eventObserver) throws ForceTransformDataException {
        EventObserver<T> forceTransform;
        try {
            forceTransform = eventObserver;
        } catch (Exception e) {
            throw new ForceTransformDataException("[eventObserver] force transform observer error. please check your input event type");
        }
        return forceTransform;
    }

    private T forceTransformData(Object data) throws ForceTransformDataException {
        T forceTransform;
        try {
            forceTransform = (T) data;
        } catch (Exception e) {
            throw new ForceTransformDataException("force transform data error. please check your input event type");
        }
        return forceTransform;
    }


    /**
     * 删除一个observerList
     *
     * @param eventObserver 观察者
     * @return 是否删除成功, 如果失败, 代表这个观察者并没有注册
     */
    public boolean removeObserver(EventObserver<T> eventObserver) {
        if (eventObserver == null) {
            throw new NullPointerException();
        }
        if (eventObserverSet.contains(eventObserver)) {
            return eventObserverSet.remove(eventObserver);
        }
        return false;
    }


    /**
     * 通知所有观察者
     *
     * @param anyData
     * @return
     */
    public boolean notifyAllObserver(Object anyData) {
        T data = forceTransformData(anyData);
        for (EventObserver<T> eventObserver : eventObserverSet) {
            eventObserver.listen(data);
        }
        return true;
    }
}