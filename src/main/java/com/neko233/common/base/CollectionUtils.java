package com.neko233.common.base;

import java.util.Collection;

public class CollectionUtils {

    private CollectionUtils() {
    }

    public static boolean isEmpty(final Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(final Collection<?> collection) {
        return !isEmpty(collection);
    }


}
