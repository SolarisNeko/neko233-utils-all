package com.neko233.common.log;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

/**
 * @author SolarisNeko
 * Date on 2022-12-17
 */
public class LogFormatSearchUtilTest {

    @Test
    public void test() {
        String log = "2022-11-01 INFO myLogger - asdflkajsdflkjasdf";
        String logFormat = "${date} ${level} ${logger} - ${msg}";

        Map<String, String> formatMap = LogFormatSearchUtil.formatLine(log, logFormat);

        Assert.assertEquals("INFO", formatMap.getOrDefault("level", ""));
        Assert.assertEquals("myLogger", formatMap.getOrDefault("logger", ""));
        Assert.assertEquals("asdflkajsdflkjasdf", formatMap.getOrDefault("msg", ""));
    }

}