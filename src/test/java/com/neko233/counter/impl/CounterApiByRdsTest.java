package com.neko233.counter.impl;

import com.neko233.counter.CounterApi;
import com.neko233.db.DataSourceMock;
import org.junit.Assert;
import org.junit.Test;

import javax.sql.DataSource;

public class CounterApiByRdsTest {
    DataSource dataSource = DataSourceMock.createDataSource();

    @Test
    public void test_plus1() throws InterruptedException {
        CounterApi counterApi = new CounterApiByRds(dataSource);

        String id = "demo" + System.currentTimeMillis();
        String type = "test_plus1";
        counterApi.set(id, type);
        counterApi.plus(id, type);
        Number value = counterApi.getValue(id, type);


        Assert.assertEquals(1, value.intValue());
    }

    @Test
    public void test_plus10() throws InterruptedException {
        CounterApi counterApi = new CounterApiByRds(dataSource);

        String id = "demo" + System.currentTimeMillis();
        String type = "test_plus10";
        counterApi.set(id, type);
        counterApi.plus(id, type, 10);
        Number value = counterApi.getValue(id, type);


        Assert.assertEquals(10, value.intValue());
    }

    @Test
    public void test_plus10_minus1() throws InterruptedException {
        CounterApi counterApi = new CounterApiByRds(dataSource);

        String id = "demo" + System.currentTimeMillis();
        String type = "test_plus10_minus1";
        counterApi.set(id, type);
        counterApi.plus(id, type, 10);
        counterApi.minus(id, type, 1);
        Number value = counterApi.getValue(id, type);


        Assert.assertEquals(9, value.intValue());
    }


}