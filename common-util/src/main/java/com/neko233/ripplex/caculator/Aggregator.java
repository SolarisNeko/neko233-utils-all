package com.neko233.ripplex.caculator;

import com.neko233.ripplex.constant.AggregateType;
import com.neko233.ripplex.strategy.merge.MergeStrategy;

import java.util.Map;
import java.util.function.BiFunction;

/**
 * @author SolarisNeko
 * Date on 2022-04-30
 */
public class Aggregator {


    /**
     * 单步聚合
     * @param aggregateMap 聚合后的数据 Map< fieldName, value >
     * @param aggTypeMap 聚合类型映射
     * @param aggColName 列名
     * @param aggValue 值
     */
    public static void aggregateByStep(Map<String, Object> aggregateMap, Map<String, AggregateType> aggTypeMap, String aggColName, Object aggValue) {
        // 1. get user AggregateType
        AggregateType aggType = aggTypeMap.get(aggColName);
        if (aggType == null) {
            return;
        }

        // 2. get Merge Strategy
        MergeStrategy mergeStrategy = MergeStrategy.choose(aggType);
        BiFunction<? super Object, ? super Object, ?> merge = mergeStrategy.getMergeBiFunction(aggValue.getClass());
        // 3. COUNT is a special type
        if (aggType == AggregateType.COUNT) {
            aggregateMap.merge(aggColName, 1, merge);
        } else if (aggType == AggregateType.COUNT_DISTINCT) {
            aggregateMap.merge(aggColName, 1, merge);
        } else {
            aggregateMap.merge(aggColName, aggValue, merge);
        }
    }


}
