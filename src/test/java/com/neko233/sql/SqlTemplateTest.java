package com.neko233.sql;

import org.junit.Assert;
import org.junit.Test;

public class SqlTemplateTest {

    @Test
    public void test_generate() {
        String sql = SqlTemplate.builder("select * From user Where name = ${name} and age = ${age} ")
                .put("name", "neko233")
                .put("age", 1)
                .build();
        Assert.assertEquals("select * From user Where name = 'neko233' and age = 1 ", sql);
    }

    @Test
    public void test_string_contains_single() {
        String sql = SqlTemplate.builder("select * From user Where name = ${name} and age = ${age} ")
                .put("name", "neko233'123")
                .put("age", 1)
                .build();
        Assert.assertEquals("select * From user Where name = 'neko233''123' and age = 1 ", sql);
    }

    @Test
    public void test_sqlInject_1_count() {
        boolean selectNameFromUserTable = SqlTemplate.isValueSqlInject("select * from demo where name = 'drop name from user_table';drop table user_table ");
        Assert.assertTrue(selectNameFromUserTable);
    }

    @Test
    public void test_sqlInject_2_count() {
        boolean selectNameFromUserTable = SqlTemplate.isValueSqlInject("select name from user_table; drop table user_table");
        Assert.assertTrue(selectNameFromUserTable);
    }

}