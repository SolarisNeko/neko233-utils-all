package com.neko233.sql;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author SolarisNeko
 * Date on 2023-01-08
 */
public class ConditionGeneratorTest {

    @Test
    public void test() {
        String demo = ConditionGenerator.condition("demo", SqlOperation.IN, "1", "2", "3");
        Assert.assertEquals("and demo in ('3','2','1') ", demo);
    }

}