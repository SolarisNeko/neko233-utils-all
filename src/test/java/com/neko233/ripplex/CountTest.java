package com.neko233.ripplex;

import com.neko233.ripplex.RippleX;
import com.neko233.ripplex.config.MeasureConfig;
import com.neko233.ripplex.constant.AggregateType;
import com.neko233.ripplex.pojo.User;
import com.neko233.ripplex.pojo.UserWithCount;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author SolarisNeko
 * Date on 2022-05-01
 */
public class CountTest {

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
        List<UserWithCount> build = RippleX.builder()
                .data(dataList)
                .dimensionColumnNames("job")
                .excludeColumnNames("id")
                .measureConfig(MeasureConfig.builder()
                        // 特殊逻辑, COUNT 会产出 2 个数据, 分别是 job -> job, count(job) -> count
                        .set("job", AggregateType.COUNT, "count")
                )
                .returnType(UserWithCount.class)
                .build();
        List<UserWithCount> ripple = build.stream()
                .sorted(UserWithCount::compareTo)
                .collect(Collectors.toList());

        // worker 3
        // boss 1
        UserWithCount data0 = ripple.get(0);
        Assert.assertEquals("boss:1", data0.getJob() + ":" + data0.getCount());
        UserWithCount data1 = ripple.get(1);
        Assert.assertEquals("worker:3", data1.getJob() + ":" + data1.getCount());
    }

}
