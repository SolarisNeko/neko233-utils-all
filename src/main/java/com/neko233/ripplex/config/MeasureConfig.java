package com.neko233.ripplex.config;


import com.neko233.ripplex.constant.AggregateType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author SolarisNeko
 * Date on 2022-05-01
 */
public class MeasureConfig {

    private final Map<String, AggregateType> aggregateTypeMap = new HashMap<>();
    private final Map<String, String> aliasColumnNameMap = new HashMap<>();

    public static MeasureConfig builder() {
        return new MeasureConfig();
    }

    /**
     * input -> output as same Name
     *
     * @param inputOutputColumnName 输入输出的列名
     * @param aggregateType         聚合类型
     * @return 配置
     */
    public MeasureConfig set(String inputOutputColumnName, AggregateType aggregateType) {
        return set(inputOutputColumnName, aggregateType, inputOutputColumnName);
    }

    public MeasureConfig set(String inputColumnName, AggregateType aggregateType, String outputColumnName) {
        aggregateTypeMap.put(inputColumnName, aggregateType);
        aliasColumnNameMap.put(inputColumnName, outputColumnName);
        return this;
    }

    public Map<String, AggregateType> build() {
        return aggregateTypeMap;
    }

    /**
     * 外部使用
     *
     * @param columnName 列名
     * @return 聚合类型
     */
    public AggregateType getAggregateType(String columnName) {
        return aggregateTypeMap.get(columnName);
    }

    public String getOutputColumnName(String inputColumnName) {
        return aliasColumnNameMap.get(inputColumnName);
    }

}
