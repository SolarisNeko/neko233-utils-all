package com.neko233.common.base;

import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;

@Slf4j
public class CloseableUtils {

    public static void autoClose(AutoCloseable... autoCloseables) {
        for (AutoCloseable autoCloseable : autoCloseables) {
            if (autoCloseable == null) {
                return;
            }
            try {
                autoCloseable.close();
            } catch (Exception e) {
                log.error("close error. class name = {}", autoCloseable.getClass(), e);
            }
        }
    }

    public static void autoClose(Closeable... autoCloseables) {
        for (AutoCloseable autoCloseable : autoCloseables) {
            if (autoCloseable == null) {
                return;
            }
            try {
                autoCloseable.close();
            } catch (Exception e) {
                log.error("close error. name = {}", autoCloseable.getClass(), e);
            }
        }
    }

}
