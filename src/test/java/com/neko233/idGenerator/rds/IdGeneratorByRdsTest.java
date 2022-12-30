package com.neko233.idGenerator.rds;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.neko233.db.DataSourceMock;
import com.neko233.idGenerator.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.Test;

import javax.sql.DataSource;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class IdGeneratorByRdsTest {

    @Test
    public void demo() throws Exception {
        Properties properties = DataSourceMock.pullMysqlConfigProperties();
        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);

        IdGenerator demoIdGenerator = new IdGeneratorByRds("demo", dataSource);
        IdGenerator first = new IdGeneratorByRds("first", dataSource);

        Long nextId = demoIdGenerator.nextId();
        log.info("id = {}", nextId);

        List<Long> idList = demoIdGenerator.nextIds(10);
        for (int i = 0; i < idList.size(); i++) {
            log.info("id = {}", idList.get(i));
        }

        log.info("-----------------------------------");


        log.info("id = {}", first.nextId());
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

        IdGenerator demoIdGenerator = new IdGeneratorByRds("step_10", dataSource, 1, true, template);

        Long nextId = demoIdGenerator.nextId();
        log.info("id = {}", nextId);

        List<Long> idList = demoIdGenerator.nextIds(10);
        for (int i = 0; i < idList.size(); i++) {
            log.info("id = {}", idList.get(i));
        }
    }

    @Test
    public void test_multiThread() throws Exception {
        DataSource dataSource = DataSourceMock.createDataSource();

        IdGenerator demoIdGenerator = new IdGeneratorByRds("test_multiThread", dataSource);

        ExecutorService es = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 4; i++) {
            es.execute(() -> {
                List<Long> idList = demoIdGenerator.nextIds(10);
                for (int j = 0; j < idList.size(); j++) {
                    log.info("id = {}", idList.get(j));
                }
            });
        }

        Thread.sleep(1000 * 2);
    }

    @Test
    public void test_singleGenerateSpeedInCache() throws Exception {
        DataSource dataSource = DataSourceMock.createDataSource();

        IdGenerator demoIdGenerator = new IdGeneratorByRds("test_speed_in_single_cache", dataSource, 10_0000, true);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<Long> idList = demoIdGenerator.nextIds(10_0000);
        System.out.println(idList.get(idList.size() - 1));
        stopWatch.stop();

        System.out.println(stopWatch.getTime(TimeUnit.MILLISECONDS));
    }


    @Test
    public void test_batchGenerateSpeed() throws Exception {
        DataSource dataSource = DataSourceMock.createDataSource();

        IdGenerator demoIdGenerator = new IdGeneratorByRds("test_speed_in_batch_cache", dataSource, 10_0000, true);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<Long> idList = demoIdGenerator.nextIds(10_0000);
        System.out.println(idList.get(idList.size() - 1));
        stopWatch.stop();

        System.out.println(stopWatch.getTime(TimeUnit.MILLISECONDS));
    }

}