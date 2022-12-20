package com.neko233.ripplex;

import com.neko233.ripplex.config.MeasureConfig;
import com.neko233.ripplex.constant.AggregateType;
import com.neko233.ripplex.pojo.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author SolarisNeko
 * Date on 2022-05-01
 */
public class MinTest {

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


        // 构建 Ripple
        List<User> build = RippleX.builder()
                .data(dataList)
                .dimensionColumnNames("job")
                .excludeColumnNames("id")
                .measureConfig(MeasureConfig.builder()
                        .set("salary", AggregateType.MIN)
                )
                .returnType(User.class)
                .build();
        List<User> ripple = build.stream()
                .sorted(User::compareTo)
                .collect(Collectors.toList());

        Assert.assertEquals(Double.valueOf(1000d), ripple.get(0).getSalary());
        Assert.assertEquals(Double.valueOf(666666d), ripple.get(1).getSalary());
    }

}
