package com.neko233.common.base;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * Key-Value 模板. placeholder = ${key}
 *
 * @author SolarisNeko on 2021-07-01
 **/
public class KvTemplate {

    private final String kvTemplate;
    private final Map<String, String> kvMap = new HashMap<>();

    public KvTemplate(String kvTemplate) {
        this.kvTemplate = kvTemplate;
    }
    
    public static KvTemplate builder(String kvTemplate) {
        if (StringUtils.isBlank(kvTemplate)) {
            throw new IllegalArgumentException("your kv template is blank !");
        }
        return new KvTemplate(kvTemplate);
    }

    public KvTemplate merge(String key, String value, BiFunction<String, String, String> merge) {
        kvMap.merge(key, value, merge);
        return this;
    }

    public KvTemplate put(String key, String value) {
        kvMap.put(key, value);
        return this;
    }

    public KvTemplate put(Map<String, String> kv) {
        if (kv == null) {
            return this;
        }
        kv.forEach(this::put);
        return this;
    }

    public String build() {
        return this.toString();
    }

    @Override
    public String toString() {
        String tempSql = kvTemplate;
        // 替换全部
        for (Map.Entry<String, String> originalKv : kvMap.entrySet()) {
            tempSql = tempSql.replaceAll(
                    // ${key}
                    "\\$\\{" + originalKv.getKey() + "\\}",
                    String.valueOf(originalKv.getValue())
            );
        }
        return tempSql;
    }
}
