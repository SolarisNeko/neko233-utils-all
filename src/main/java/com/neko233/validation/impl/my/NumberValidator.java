package com.neko233.validation.impl.my;

import com.neko233.validation.ValidateApi;
import com.neko233.validation.annotation.ValidateNumber;
import com.neko233.common.base.StringUtils233;

/**
 * ValidateNumber Validator
 */
public class NumberValidator implements ValidateApi<ValidateNumber, Number> {

    @Override
    public Class<? extends ValidateNumber> getAnnotationType() {
        return ValidateNumber.class;
    }

    @Override
    public boolean handle(ValidateNumber annotation, Number fieldValue) {
        int min = annotation.min();
        int max = annotation.max();

        if (min == 0 && max == 0) {
            return true;
        }

        int value = fieldValue.intValue();
        if (value < min) {
            return false;
        }
        if (value > max) {
            return false;
        }
        return true;
    }

    @Override
    public String getReason(ValidateNumber annotation, Number fieldValue) {
        if (StringUtils233.isNotBlank(annotation.tips())) {
            return annotation.tips();
        }
        return String.format("Your number is not in constraint. min = %s, max = %s, your value = %s",
                annotation.min(), annotation.max(), fieldValue);
    }

}
