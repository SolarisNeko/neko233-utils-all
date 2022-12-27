package com.neko233.common.rateLimit;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class LazyTokenRateLimiterTest {

    @Test
    public void test1() {
        int successCount = 0;

        LazyTokenRateLimiter limiter = new LazyTokenRateLimiter(1);

        for (int i = 0; i < 10; i++) {
            boolean isSuccess = limiter.tryAcquire();
            if (!isSuccess) {
                continue;
            }

            successCount += 1;
            // 获取成功往下执行
        }

        Assert.assertEquals(1, successCount);
    }

}