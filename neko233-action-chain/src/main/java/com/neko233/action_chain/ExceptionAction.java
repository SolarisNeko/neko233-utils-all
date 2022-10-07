package com.neko233.action_chain;

@FunctionalInterface
public interface ExceptionAction {

    void handleException(Exception e);

}