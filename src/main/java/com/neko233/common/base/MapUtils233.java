package com.neko233.common.base;

import java.util.Map;

public class MapUtils233 {

    private MapUtils233() {
    }

    public static boolean isEmpty(final Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNotEmpty(final Map<?, ?> collection) {
        return !isEmpty(collection);
    }


}
