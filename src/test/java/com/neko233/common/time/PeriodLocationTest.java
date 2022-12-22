package com.neko233.common.time;

import com.neko233.common.time.Period;
import com.neko233.common.time.PeriodLocation;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author SolarisNeko
 * Date on 2022-12-15
 */
public class PeriodLocationTest {

    @Test
    public void testPeriod() {
        long startMs = new DateTime("2022-01-01").toInstant().getMillis();
        long endMs = new DateTime("2023-01-01").toInstant().getMillis();
        long nowMs = new DateTime("2022-12-31").toInstant().getMillis();

        PeriodLocation periodLocation = Period.calculatePeriod(nowMs, startMs, endMs, 1, TimeUnit.DAYS);

        Assert.assertTrue(periodLocation.getIsInPeriod());
        Assert.assertEquals(periodLocation.getPeriodMs(), Long.valueOf(86400 * 1000));
        Assert.assertEquals(periodLocation.getAllPeriodCount(), Long.valueOf(366));
        Assert.assertEquals(periodLocation.getCurrentPeriodCount(), Long.valueOf(365));
    }

    @Test
    public void testErrorTimestamp() {
        long startMs = new DateTime("2022-01-01").toInstant().getMillis();
        long endMs = new DateTime("1970-01-01").toInstant().getMillis();
        long nowMs = new DateTime("2022-12-31").toInstant().getMillis();

        PeriodLocation periodLocation = Period.calculatePeriod(nowMs, startMs, endMs, 1, TimeUnit.DAYS);

        Assert.assertFalse(periodLocation.getIsInPeriod());
        Assert.assertEquals(periodLocation.getPeriodMs(), Long.valueOf(86400 * 1000));
        Assert.assertEquals(periodLocation.getAllPeriodCount(), Long.valueOf(0));
        Assert.assertEquals(periodLocation.getCurrentPeriodCount(), Long.valueOf(0));
    }

}