package com.neko233.ripplex.strategy.merge;


import com.neko233.ripplex.constant.AggregateType;
import com.neko233.ripplex.exception.RippleException;

import java.util.function.BiFunction;

/**
 * @author SolarisNeko
 * Date on 2022-04-30
 */
public interface MergeStrategy {


    BiFunction<? super Object, ? super Object, ?> getMergeBiFunction(Class sumType);


    static MergeStrategy choose(AggregateType aggType) {
        switch (aggType) {
            case KEEP_FIRST: {
                return KeepFirstMergeStrategy.getInstance();
            }
            case SUM: {
                return SumMergeStrategy.getInstance();
            }
            case COUNT: {
                return CountMergeStrategy.getInstance();
            }
            case MAX: {
                return MaxMergeStrategy.getInstance();
            }
            case MIN: {
                return MinMergeStrategy.getInstance();
            }
            default: {
                throw new RippleException("Can't not find aggregate Type = " + aggType);
            }
        }
    }


}
