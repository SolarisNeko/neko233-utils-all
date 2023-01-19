package com.neko233.common.base;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author SolarisNeko
 * Date on 2023-01-19
 */
public class StringUtils233Test {

    @Test
    public void test_toBigCamelCaseUpper() {
        String systemUser = StringUtils233.toBigCamelCaseUpper("SystemUser");
        Assert.assertEquals("SYSTEM_USER", systemUser);
    }

    @Test
    public void test_toBigCamelCaseLower() {
        String systemUser = StringUtils233.toBigCamelCaseLower("SystemUser");
        Assert.assertEquals("system_user", systemUser);
    }

}