package com.neko233.action_chain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author LuoHaoJun on 2022-09-30
 **/
public class ActionChain {

    private static final Logger log = LoggerFactory.getLogger(ActionChain.class);

    private boolean isHandleException = false;

    private int retryCount = 0;

    private int retryIntervalSecond = 1;

    /**
     * 最大重试次数
     */
    private final int maxRetryTime = 100;

    private final List<ExecuteAction> actionList = new LinkedList<>();
    private final List<ExceptionAction> handleExceptionChain = new LinkedList<>();
    private final List<FinallyAction> finallyActionChain = new LinkedList<>();

    private ActionChain() {

    }

    public static ActionChain create() {
        return new ActionChain();
    }

    public ActionChain success(ExecuteAction action) {
        actionList.add(action);
        return this;
    }

    public ActionChain exception(ExceptionAction action) {
        isHandleException = true;
        handleExceptionChain.add(action);
        return this;
    }

    public ActionChain doFinally(FinallyAction action) {
        finallyActionChain.add(action);
        return this;
    }

    public ActionChain retry(int count) {
        retryCount = count;
        return this;
    }

    public ActionChain retry(int count, int retryIntervalSecond) {
        retryCount = count;
        this.retryIntervalSecond = retryIntervalSecond;
        return this;
    }

    public ActionChain closable(AtomicReference<Closeable> atomicRef) {
        if (atomicRef == null) {
            return this;
        }
        closable(atomicRef.get());
        return this;
    }

    public ActionChain closable(Closeable closeable) {
        finallyActionChain.add(() -> {
            try {
                if (closeable == null) {
                    return;
                }
                closeable.close();
            } catch (IOException e) {
                log.error("Closable close error.", e);

                for (ExceptionAction exceptionAction : handleExceptionChain) {
                    exceptionAction.handleException(e);
                }
            }
        });
        return this;
    }

    public void execute() {
        if (actionList.size() == 0) {
            return;
        }

        boolean isOk = true;
        try {
            executeActionChainWithRetry(isOk);
        } catch (Exception e) {
            if (!isHandleException) {
                return;
            }
            for (ExceptionAction exceptionAction : handleExceptionChain) {
                if (exceptionAction == null) {
                    continue;
                }
                exceptionAction.handleException(e);
            }
        } finally {
            for (FinallyAction finallyAction : finallyActionChain) {
                try {
                    finallyAction.doFinally();
                } catch (Exception e) {
                    for (ExceptionAction exceptionAction : handleExceptionChain) {
                        exceptionAction.handleException(e);
                    }
                }
            }
        }
    }

    private void executeActionChainWithRetry(boolean isOk) throws Exception {
        for (int count = 0; count < retryCount; count++) {
            if (count > 0) {
                log.debug("[ActionChain] actions execute error. Retry times = {}, timeUnit = {} second", count, retryIntervalSecond);
            }

            try {
                for (ExecuteAction action : actionList) {
                    if (action == null) {
                        continue;
                    }
                    action.execute();
                }
            } catch (Exception e) {
                isOk = false;
                TimeUnit.SECONDS.sleep(retryIntervalSecond);
            }

            if (isOk) {
                break;
            }
        }
    }

}
