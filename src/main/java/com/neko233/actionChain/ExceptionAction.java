package com.neko233.actionChain;

@FunctionalInterface
public interface ExceptionAction {

    void handleException(Throwable e);

}