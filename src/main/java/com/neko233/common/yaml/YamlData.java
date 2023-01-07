package com.neko233.common.yaml;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author SolarisNeko
 * Date on 2023-01-08
 */
@Slf4j

public class YamlData {

    private final static String DEFAULT_KEY_STRING_SEPARATOR = ".";

    private final Map<String, Object> configurationFile = new LinkedHashMap<>();

    public YamlData(String configFileName) {
        this.setConfigFile(configFileName);
    }

    private void setConfigFile(String configFileName) {
        final String yamlStandardExtension = "yaml";
        final String yamlSimplifyExtension = "yml";

        if (!configFileName.endsWith(yamlSimplifyExtension) && !configFileName.endsWith(yamlStandardExtension)) {
            throw new IllegalArgumentException("only supported yaml file");
        }

        InputStream configurationInputStream = YamlData.class.getClassLoader().getResourceAsStream(configFileName);
        if (configurationInputStream == null) {
            throw new IllegalArgumentException("configuration file with name " + configFileName + " not found");
        }
        Yaml yamlFile = new Yaml();
        Map<String, Object> load = yamlFile.load(configurationInputStream);
        configurationFile.putAll(load);
    }


    public Boolean getBoolean(String key) {
        return this.getBooleanOrDefault(key, false);
    }

    public Boolean getBooleanOrDefault(String key, Boolean value) {
        return Optional.ofNullable(this.getValue(key, Boolean.class)).orElse(value);
    }

    public String getString(String key) {
        return this.getStringOrDefault(key, StringUtils.EMPTY);
    }

    public String getStringOrDefault(String key, String defaultValue) {
        return Optional.ofNullable(this.getValue(key, String.class)).orElse(defaultValue);
    }

    public List<String> getStringList(String key) {
        List<Object> valueList = Optional.ofNullable(this.getValue(key, List.class)).orElse(Collections.emptyList());
        return valueList.stream().map(Object::toString).collect(Collectors.toList());
    }

    public Integer getInteger(String key) {
        return this.getIntegerOrDefault(key, 0);
    }

    public Integer getIntegerOrDefault(String key, Integer defaultValue) {
        return Optional.ofNullable(this.getValue(key, Integer.class)).orElse(defaultValue);
    }

    public Double getDouble(String key) {
        return this.getDoubleOrDefault(key, 0D);
    }

    public Double getDoubleOrDefault(String key, Double defaultValue) {
        return Optional.ofNullable(this.getValue(key, Double.class)).orElse(defaultValue);
    }

    public Map<String, String> getAllValue() {
        return this.getAllValue(configurationFile, null, new HashMap<>(16));
    }

    private <T> T getValue(String targetKey, Class<T> targetType) {
        String[] separatedKeyList = targetKey.split("\\" + DEFAULT_KEY_STRING_SEPARATOR);
        Object targetValue = configurationFile;
        for (String separatedKey : separatedKeyList) {
            if (targetValue instanceof LinkedHashMap) {
                targetValue = ((LinkedHashMap<String, Object>) targetValue).get(separatedKey);
            } else {
                return null;
            }
        }

        if (targetType.isInstance(targetValue)) {
            return (T) targetValue;
        } else {
            log.warn("trying to return a " + targetType.getSimpleName() + " value but get " + targetValue.getClass().getSimpleName());
            return null;
        }
    }

    private Map<String, String> getAllValue(Map<String, Object> configurations, String prefix, Map<String, String> dataMap) {
        if (Objects.nonNull(configurations)) {
            for (Map.Entry<String, Object> entry : configurations.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (value instanceof LinkedHashMap) {
                    dataMap = this.getAllValue((LinkedHashMap<String, Object>) value,
                            this.linkedKeyString(prefix, key), dataMap);
                } else {
                    dataMap.put(this.linkedKeyString(prefix, key), value.toString());
                }
            }
        }
        return dataMap;
    }

    private String linkedKeyString(String prefix, String keyString) {
        return null == prefix || "".equals(prefix.trim()) ? keyString : prefix + DEFAULT_KEY_STRING_SEPARATOR + keyString;
    }
}
