package com.neko233.idGenerator.rds;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.neko233.db.DataSourceMock;
import com.neko233.idGenerator.IdGenerator;
import com.neko233.idGenerator.IdGeneratorException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.joda.time.LocalDateTime;
import org.junit.Assert;
import org.junit.Test;

import javax.sql.DataSource;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class IdGeneratorByRdsTest {


    private String createFirstBusinessName(String demo) {
        return demo + "|" + LocalDateTime.now();
    }

    @Test
    public void demo() throws Exception {
        Properties properties = DataSourceMock.pullMysqlConfigProperties();
        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);


        String businessName = createFirstBusinessName("demo");
        IdGenerator demoIdGenerator = new IdGeneratorByRds(businessName, dataSource);

        Long nextId = demoIdGenerator.nextId();
        log.info("id = {}", nextId);

        List<Long> idList = demoIdGenerator.nextIds(10);
        Assert.assertEquals(10, idList.size());

    }


    @Test
    public void test2() throws Exception {
        Properties properties = DataSourceMock.pullMysqlConfigProperties();
        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);

        IdGeneratorEntity template = IdGeneratorEntity.builder()
                .startId(1)
                .currentId(1)
                .endId(100_0000)
                .step(10)
                .build();


        String businessName = createFirstBusinessName("step_10");
        IdGenerator demoIdGenerator = new IdGeneratorByRds(businessName, dataSource, 1, true, template);

        Long nextId = demoIdGenerator.nextId();
        log.info("id = {}", nextId);

        int idSize = 10;
        List<Long> idList = demoIdGenerator.nextIds(idSize);
//        for (int i = 0; i < idList.size(); i++) {
//            log.info("id = {}", idList.get(i));
//        }

        Assert.assertEquals(idSize, idList.size());
    }

    @Test
    public void test_multiThread() throws Exception {
        DataSource dataSource = DataSourceMock.createDataSource();

        String businessName = createFirstBusinessName("test_multiThread");
        IdGenerator demoIdGenerator = new IdGeneratorByRds(businessName, dataSource);

        AtomicInteger count = new AtomicInteger();
        ExecutorService es = Executors.newFixedThreadPool(4);
        // sum = 40
        for (int i = 0; i < 4; i++) {
            es.execute(() -> {
                List<Long> idList = null;
                try {
                    idList = demoIdGenerator.nextIds(10);
                } catch (IdGeneratorException e) {

                }
                for (int j = 0; j < idList.size(); j++) {
                    count.getAndIncrement();
                }
            });
        }

        Thread.sleep(1000 * 2);
        Assert.assertEquals(40, count.get());
    }

    @Test
    public void test_singleGenerateSpeedInCache() throws Exception {
        DataSource dataSource = DataSourceMock.createDataSource();

        String businessName = createFirstBusinessName("test_speed_in_single_cache");
        IdGenerator demoIdGenerator = new IdGeneratorByRds(businessName, dataSource, 10_0000, true);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<Long> idList = demoIdGenerator.nextIds(10_0000);
//        System.out.println(idList.get(idList.size() - 1));
        Assert.assertEquals(10_0000, idList.size());
        stopWatch.stop();

        System.out.println("spend ms = " + stopWatch.getTime(TimeUnit.MILLISECONDS));
    }


    @Test
    public void test_batchGenerateSpeed() throws Exception {
        DataSource dataSource = DataSourceMock.createDataSource();


        String businessName = createFirstBusinessName("test_speed_in_single_cache");
        IdGenerator demoIdGenerator = new IdGeneratorByRds(businessName, dataSource, 10_0000, true);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<Long> idList = demoIdGenerator.nextIds(10_0000);
        Assert.assertEquals(10_0000, idList.size());
        stopWatch.stop();
        System.out.println("spend ms = " + stopWatch.getTime(TimeUnit.MILLISECONDS));
    }

}