package com.neko233.common.pid;

import com.neko233.common.base.StringUtils233;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author SolarisNeko
 * Date on 2022-12-17
 */
public class ProcessIdUtilTest {

    @Test
    public void test() {
        String processId = ProcessIdUtil.getProcessId();

        Assert.assertTrue(StringUtils233.isNotBlank(processId));
    }

}