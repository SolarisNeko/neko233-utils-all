package com.neko233.sql;


import com.alibaba.fastjson2.JSON;
import com.neko233.common.base.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LuoHaoJun on 2022-08-01
 **/
public class SqlTemplate {

    private static final String SQL_INJECT_REGEX = ".*(exec|insert|select|delete|drop|update|count|%|chr|mid|master|truncate|char|declare|;) .*";

    private final String sqlTemplate;
    private final Map<String, Object> kvMap = new HashMap<>();

    public SqlTemplate(String sqlTemplate) {
        this.sqlTemplate = sqlTemplate;
    }

    public static boolean isValueSqlInject(String sql) {
        return sql.toLowerCase().matches(SQL_INJECT_REGEX);
    }

    public static SqlTemplate builder(String sqlTemplate) {
        if (StringUtils.isBlank(sqlTemplate)) {
            throw new RuntimeException("your sql template is blank !");
        }
        return new SqlTemplate(sqlTemplate);
    }

    public SqlTemplate put(String key, Object value) {
        kvMap.put(key, value);
        return this;
    }

    public SqlTemplate merge(String key, Object value) {
        return merge(key, value, "");
    }

    public SqlTemplate merge(String key, Object value, String union) {
        kvMap.merge(key, value, (v1, v2) -> v1 + union + v2);
        return this;
    }

    public String build() {
        return this.toString();
    }

    @Override
    public String toString() throws IllegalArgumentException {
        String tempSql = sqlTemplate;
        // 替换全部
        for (Map.Entry<String, Object> kv : kvMap.entrySet()) {
            String value;
            Object valueObj = kv.getValue();
            if (valueObj instanceof String) {
                value = "'" + String.valueOf(valueObj).replaceAll("'", "''") + "'";
            } else {
                value = String.valueOf(valueObj);
            }

            if (isValueSqlInject(value)) {
                throw new IllegalArgumentException(
                        String.format("[SqlTemplate] SQL Inject Error. Please check. sql Template = %s. original K-V Map = %s"
                                , sqlTemplate, JSON.toJSONString(kvMap))
                );
            }
            tempSql = tempSql.replaceAll(
                    "\\$\\{" + kv.getKey() + "\\}",
                    value
            );
        }
        return tempSql;
    }
}