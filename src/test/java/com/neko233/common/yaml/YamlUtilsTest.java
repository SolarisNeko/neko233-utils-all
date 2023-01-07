package com.neko233.common.yaml;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author SolarisNeko
 * Date on 2023-01-08
 */
public class YamlUtilsTest {

    @Test
    public void read() {
        YamlData read = YamlUtils.read("application.yaml");
        String serverName = read.getString("server.name");
        Assert.assertEquals("neko233-server", serverName);
    }
}