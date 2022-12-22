package com.neko233.ripplex;

import com.neko233.ripplex.RippleX;
import com.neko233.ripplex.config.MeasureConfig;
import com.neko233.ripplex.constant.AggregateType;
import com.neko233.ripplex.pojo.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class KeepTest {

    // group by 字段做 distinct 处理, 其余做 aggregate 操作
    @Test
    public void test() {
        /**
         * Data
         */
        List<User> dataList = new ArrayList<User>() {{
            add(User.builder().id(1).name("neko").job("worker").age(10).salary(1000d).build());
            add(User.builder().id(2).name("doge").job("worker").age(20).salary(2000d).build());
            add(User.builder().id(3).name("doge").job("worker").age(30).salary(1000d).build());
            add(User.builder().id(4).name("boss").job("boss").age(40).salary(666666d).build());
        }};


        // Ripple 水波
        List<User> build = RippleX.builder()
                .data(dataList)
                .dimensionColumnNames("name")
//                .excludeColumnNames("id")
                .measureConfig(MeasureConfig.builder()
                        .set("id", AggregateType.KEEP_FIRST)
                        .set("name", AggregateType.KEEP_FIRST)
                        // Sum
                        .set("age", AggregateType.SUM, "age")
                )
                .returnType(User.class)
                .build();
        List<User> ripple = build.stream()
                .sorted(User::compareTo)
                .collect(Collectors.toList());

        Assert.assertEquals(Integer.valueOf(40), ripple.get(0).getAge());
        Assert.assertEquals(Integer.valueOf(50), ripple.get(1).getAge());
        Assert.assertEquals(Integer.valueOf(10), ripple.get(2).getAge());
    }

}
