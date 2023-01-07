package com.neko233.common.base;

import java.util.Collection;

public class CollectionUtils233 {

    private CollectionUtils233() {
    }

    public static boolean isEmpty(final Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(final Collection<?> collection) {
        return !isEmpty(collection);
    }


}
