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

public class SumTest {

    // group by 字段做 distinct 处理, 其余做 aggregate 操作
    @Test
    public void test() {


        /**
         * Data
         */
        // group by a, b, c, d | json -> a, b |
        List<User> dataList = new ArrayList<User>() {{
            add(User.builder().id(1).name("neko").job("worker").age(18).salary(1000d).build());
            add(User.builder().id(2).name("doge").job("worker").age(30).salary(2000d).build());
            add(User.builder().id(3).name("duck").job("worker").age(40).salary(1000d).build());
            add(User.builder().id(4).name("boss").job("boss").age(66).salary(666666d).build());
        }};


        // Ripple 水波
        // TODO ORM test
        List<User> build = RippleX.builder()
                .data(dataList)
                .dimensionColumnNames("job")
                .excludeColumnNames("id")
                .measureConfig(MeasureConfig.builder()
                        .set("age", AggregateType.SUM, "age")
                        .set("salary", AggregateType.SUM, "salary")
                )
                .returnType(User.class)
                .build();

        List<User> ripple = build.stream()
                .sorted(User::compareTo)
                .collect(Collectors.toList());

        Double salary0 = ripple.get(0).getSalary();
        Assert.assertEquals(Double.valueOf(4000d), salary0);

        Double salary1 = ripple.get(1).getSalary();
        Assert.assertEquals(Double.valueOf(666666d), salary1);
    }

}
