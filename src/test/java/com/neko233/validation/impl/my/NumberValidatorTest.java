package com.neko233.validation.impl.my;

import com.neko233.validation.ValidateContext;
import com.neko233.validation.Validator;
import com.neko233.validation.annotation.ValidateNumber;
import org.junit.Assert;
import org.junit.Test;

public class NumberValidatorTest {

    class Demo {
        @ValidateNumber(min = 1, max = 99)
        public int count;
    }

    @Test
    public void testTrue() {
        Validator.scanPackage(NumberValidator.class.getPackage().getName());
        Demo demo = new Demo();
        demo.count = 86;

        ValidateContext validate = Validator.validate(demo);
        Assert.assertTrue(validate.isOk());
    }

    @Test
    public void testFailure() {
        Validator.scanPackage(NumberValidator.class.getPackage().getName());
        Demo demo = new Demo();
        demo.count = 101;

        ValidateContext validate = Validator.validate(demo);
        Assert.assertFalse(validate.isOk());
    }
}