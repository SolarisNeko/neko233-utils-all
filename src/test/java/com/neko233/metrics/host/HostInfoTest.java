package com.neko233.metrics.host;

import org.junit.Test;

import static org.junit.Assert.*;

public class HostInfoTest {

    public static final HostInfo hostInfo = new HostInfo();

    @Test
    public void getName() {
        String name = hostInfo.getName();
//        System.out.println(name);
    }

    @Test
    public void getAddress() {
        String address = hostInfo.getAddress();
//        System.out.println(address);
    }
}