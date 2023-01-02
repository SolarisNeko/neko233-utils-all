package com.neko233.delegate;

import com.neko233.mockData.MockDataDto;
import com.neko233.event.delegate.EventDispatcher;
import com.neko233.event.exception.RegisterDuplicateException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author SolarisNeko
 * Date on 2022-10-30
 */
@Slf4j
public class EventDispatcherTest {

    private final EventDispatcher eventDispatcher = new EventDispatcher();

    MockDataDto data = MockDataDto.builder().name("neko233").build();

    @Test
    public void test_register() throws RegisterDuplicateException {
        AtomicReference<String> demo = new AtomicReference<>("");
        eventDispatcher.register(MockDataDto.class, (anyData -> demo.set("demo")));

        eventDispatcher.sync(data);

        Assert.assertEquals("demo", demo.get());
    }

    @Test
    public void test_unregister() {
        AtomicReference<String> demo = new AtomicReference<>("");
        eventDispatcher.register(MockDataDto.class, (anyData -> demo.set("demo")));
        eventDispatcher.unregisterAll(MockDataDto.class);

        eventDispatcher.sync(data);

        Assert.assertEquals("", demo.get());
    }

    @Test
    public void test_async() throws InterruptedException {
        AtomicReference<String> demo = new AtomicReference<>("");
        eventDispatcher.register(MockDataDto.class, (anyData -> demo.set("demo")));

        eventDispatcher.async(data);

        // wait async
        Thread.sleep(200);
        Assert.assertEquals("demo", demo.get());
    }

    @Test
    public void test_async_unregister() throws InterruptedException {
        AtomicReference<String> demo = new AtomicReference<>("");
        eventDispatcher.register(MockDataDto.class, (anyData -> demo.set("demo")));
        eventDispatcher.unregisterAll(MockDataDto.class);

        eventDispatcher.async(data);

        // wait async
        Thread.sleep(200);
        Assert.assertEquals("", demo.get());
    }
}