package com.neko233.validation;

import com.neko233.validation.impl.JakartaEmailValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

@Slf4j
public class ValidatorTest {

    @Test
    public void test_gmail_Format_to_QQ_return_false() {
        Validator.scanPackage(JakartaEmailValidator.class.getPackage().getName());
        ValidateDto build = ValidateDto.builder()
                .email("123@gmail.com")
                .build();
        ValidateContext validate = Validator.validate(build);
        Assert.assertFalse(validate.isOk());
    }

    /**
     * 速度测试
     */
    @Test
    public void testSpeedByJitOptimize() {
        Validator.scanPackage(JakartaEmailValidator.class.getPackage().getName());

        ValidateDto build = ValidateDto.builder()
                .email("123@gmail.com")
                .build();

        // 1st, 1w times = 100 ms
        // 5th, 1w times = 16 ms ~= 70w/s
        StopWatch sw = new StopWatch();
        for (int j = 0; j < 5; j++) {
            sw.start();
            for (int i = 0; i < 10000; i++) {
                ValidateContext validate = Validator.validate(build);
            }
            sw.stop();
            long time = sw.getTime(TimeUnit.MILLISECONDS);
            log.info("spend ms = {}", time);
            sw.reset();
        }

    }

}