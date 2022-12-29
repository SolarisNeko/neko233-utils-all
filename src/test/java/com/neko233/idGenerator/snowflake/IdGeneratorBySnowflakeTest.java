package com.neko233.idGenerator.snowflake;

import org.junit.Test;

public class IdGeneratorBySnowflakeTest {

    @Test
    public void test() {
        IdGeneratorBySnowflake worker = new IdGeneratorBySnowflake("demo", 1);
        for (int i = 0; i < 30; i++) {
            System.out.println(worker.nextId());
        }
    }

}