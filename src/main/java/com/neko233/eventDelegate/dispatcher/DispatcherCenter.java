package com.neko233.eventDelegate.dispatcher;

import com.neko233.eventDelegate.exception.RegisterDuplicateException;
import com.neko233.eventDelegate.delegate.AbstractDelegateManager;
import com.neko233.eventDelegate.delegate.EventObserver;
import com.neko233.eventDelegate.pool.MyThreadPoolFactory;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Singleton
 *
 * @author SolarisNeko
 * Date on 2022-10-30
 */
public class DispatcherCenter {

    private final ThreadPoolExecutor asyncThreadPool = MyThreadPoolFactory.getThreadPoolOrDefault(this.getClass().getName());

    /**
     * Lazy init in singleton
     */
    private static final class SingletonHolder {
        static final DispatcherCenter singleton = new DispatcherCenter();
    }

    public static DispatcherCenter getInstance() {
        return SingletonHolder.singleton;
    }

    private DispatcherCenter() {
    }

    /**
     * k-v for dispatcher choose delegate chain .
     */
    private final Map<String, AbstractDelegateManager<?>> dispatcherMap = new ConcurrentHashMap<>();


    /**
     * 委托执行
     *
     * @param key  唯一标识
     * @param data 数据
     */
    public void delegateHandleInSync(String key, Object data) {
        AbstractDelegateManager<?> manager = checkObserverIsBlank(key);
        if (manager == null) {
            return;
        }

        manager.notifyAllObserver(data);
    }


    /**
     * 异步执行
     *
     * @param key  唯一标识
     * @param data 任意数据
     * @return Future
     */
    public Future<?> delegateHandleInAsync(String key, Object data) {
        return asyncThreadPool.submit(() -> {
            delegateHandleInSync(key, data);
        });
    }


    /**
     * 注册 observer Manager 到 Dispatcher
     *
     * @param key     唯一标识名
     * @param manager 管理器
     * @return this
     * @throws RegisterDuplicateException 重复注册异常
     */
    public synchronized DispatcherCenter registerObserverManager(String key, AbstractDelegateManager<?> manager) throws RegisterDuplicateException {
        if (Objects.nonNull(dispatcherMap.get(key))) {
            throw new RegisterDuplicateException(String.format("register key = `%s` is duplicate.", key));
        }
        dispatcherMap.putIfAbsent(key, manager);
        return this;
    }


    /**
     * 取消注册
     *
     * @param key 唯一标识名
     * @return this
     */
    public synchronized DispatcherCenter unRegisterObserverManager(String key) {
        if (Objects.isNull(dispatcherMap.get(key))) {
            return this;
        }
        dispatcherMap.remove(key);
        return this;
    }


    /**
     * @param key           manager 的唯一标识
     * @param eventObserver 事件监听器
     * @return isOk?
     */
    public boolean addObserverByKey(String key, EventObserver<?> eventObserver) {
        AbstractDelegateManager<?> manager = checkObserverIsBlank(key);
        if (manager == null) {
            return false;
        }
        return manager.addObserver(eventObserver);
    }


    /**
     * @param key           manager 的唯一标识
     * @param eventObserver 事件监听器
     * @return isOk?
     */
    public boolean removeObserverByKey(String key, EventObserver<?> eventObserver) {
        AbstractDelegateManager<?> manager = checkObserverIsBlank(key);
        if (manager == null) {
            return false;
        }
        return manager.addObserver(eventObserver);
    }


    /**
     * 通用检查
     *
     * @param key 唯一标识
     * @return 管理器
     */
    private AbstractDelegateManager<?> checkObserverIsBlank(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        return dispatcherMap.get(key);
    }

}
