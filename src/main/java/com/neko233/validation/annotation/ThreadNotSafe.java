package com.neko233.validation.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ThreadNotSafe {
}
