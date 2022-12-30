package com.neko233.delegate;

import com.neko233.delegate.mock.TestDelegateManager;
import com.neko233.delegate.mock.MockDataDto;
import com.neko233.eventDelegate.delegate.DispatcherCenter;
import com.neko233.eventDelegate.exception.RegisterDuplicateException;
import org.junit.Test;

/**
 * @author SolarisNeko
 * Date on 2022-10-30
 */
public class DispatcherCenterTest {

    private final DispatcherCenter dispatcherCenter = DispatcherCenter.getInstance();

    @Test
    public void registerObserverManager() throws RegisterDuplicateException {
        TestDelegateManager manager = new TestDelegateManager();
        manager.addObserver((anyData -> {
            System.out.println("test --- " + anyData);
        }));
        dispatcherCenter.registerObserverManager("test", manager);

        // test
        MockDataDto data = MockDataDto.builder().name("neko233").build();
        dispatcherCenter.delegateHandleInSync("test", data);

    }

    @Test
    public void unRegisterObserverManager() {
    }

    @Test
    public void addObserverByKey() {
    }

    @Test
    public void removeObserverByKey() {
    }
}