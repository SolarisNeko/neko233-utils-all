package com.neko233.common.base;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtilsTest {

    @Test
    public void test_success_properties() throws IOException {

        Properties properties1 = PropertiesUtils.loadProperties("i18n_en.properties");

        Assert.assertEquals("neko233", properties1.get("name"));
    }


}