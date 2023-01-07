package com.neko233.ripplex.strategy.transform;

import com.neko233.ripplex.exception.RippleException;
import com.neko233.common.base.StringUtils233;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author SolarisNeko
 * Date on 2022-04-30
 */
public interface TransformValueStrategy {

    static Object transform(Class returnType, Object value) {
        String valueString = String.valueOf(value);
        if (StringUtils233.isBlank(valueString) || valueString.equals("null")) {
            return null;
        }
        switch (returnType.getName()) {
            case "byte":
            case "Byte":
            case "java.lang.Byte": {
                return Byte.valueOf(valueString);
            }
            case "boolean":
            case "Boolean":
            case "java.lang.Boolean": {
                return Boolean.valueOf(valueString);
            }
            case "short":
            case "Short":
            case "java.lang.Short": {
                return Short.valueOf(valueString);
            }
            case "int":
            case "Integer":
            case "java.lang.Integer": {
                return Integer.valueOf(valueString);
            }
            case "float":
            case "Float":
            case "java.lang.Float": {
                return Float.valueOf(valueString);
            }
            case "double":
            case "Double":
            case "java.lang.Double": {
                return Double.valueOf(valueString);
            }
            case "long":
            case "Long":
            case "java.lang.Long": {
                return Long.valueOf(valueString);
            }
            case "String":
            case "java.lang.String": {
                return valueString;
            }
            case "BigDecimal":
            case "java.math.BigDecimal": {
                return new BigDecimal(valueString);
            }
            case "java.math.BigInteger": {
                return new BigInteger(valueString);
            }
            default: {
                throw new RippleException("Can't not find transform type Class = " + returnType.getName());
            }
        }
    }

}
