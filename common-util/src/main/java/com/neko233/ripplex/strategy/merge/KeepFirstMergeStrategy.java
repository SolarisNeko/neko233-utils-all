package com.neko233.ripplex.strategy.merge;

import java.util.function.BiFunction;

/**
 * @author SolarisNeko
 * Date on 2022-04-30
 */
public class KeepFirstMergeStrategy implements MergeStrategy {

    private static final MergeStrategy INSTANCE = new KeepFirstMergeStrategy();

    public static MergeStrategy getInstance() {
        return INSTANCE;
    }

    @Override
    public BiFunction<? super Object, ? super Object, ?> getMergeBiFunction(Class sumType) {
        return (t1, t2) -> t1;
    }
}
