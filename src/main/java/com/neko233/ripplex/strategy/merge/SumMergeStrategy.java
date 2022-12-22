package com.neko233.ripplex.strategy.merge;

import com.neko233.ripplex.exception.RippleException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.function.BiFunction;

/**
 * @author SolarisNeko
 * Date on 2022-04-30
 */
public class SumMergeStrategy implements MergeStrategy {

    private static final MergeStrategy INSTANCE = new SumMergeStrategy();

    public static MergeStrategy getInstance() {
        return INSTANCE;
    }

    @Override
    public BiFunction<? super Object, ? super Object, ?> getMergeBiFunction(Class sumType) {
        switch (sumType.getName()) {
            case "short":
            case "Short":
            case "java.lang.Short": {
                return (t1, t2) -> (Short) t1 + (Short) t2;
            }
            case "int":
            case "Integer":
            case "java.lang.Integer": {
                return (t1, t2) -> (Integer) t1 + (Integer) t2;
            }
            case "float":
            case "Float":
            case "java.lang.Float": {
                return (t1, t2) -> (Float) t1 + (Float) t2;
            }
            case "double":
            case "Double":
            case "java.lang.Double": {
                return (t1, t2) -> (Double) t1 + (Double) t2;
            }
            case "long":
            case "Long":
            case "java.lang.Long": {
                return (t1, t2) -> (Long) t1 + (Long) t2;
            }
            case "String":
            case "java.lang.String": {
                return (t1, t2) -> (String) t1 + (String) t2;
            }
            case "BigDecimal":
            case "java.math.BigDecimal": {
                return (t1, t2) -> {
                    BigDecimal tempValue = (BigDecimal) t1;
                    tempValue.add((BigDecimal) t2);
                    return tempValue;
                };
            }
            case "java.math.BigInteger": {
                return (t1, t2) -> {
                    BigInteger tempValue = (BigInteger) t1;
                    tempValue.add((BigInteger) t2);
                    return tempValue;
                };
            }
            default: {
                throw new RippleException("Un-support " + sumType.getName() + " to merge.");
            }
        }
    }
}
