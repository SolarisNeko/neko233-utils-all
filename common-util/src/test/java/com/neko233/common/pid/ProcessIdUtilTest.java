package com.neko233.common.pid;

import org.apache.commons.lang3.StringUtils;
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

        Assert.assertTrue(StringUtils.isNotBlank(processId));
    }

}