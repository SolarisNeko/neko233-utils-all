package com.neko233.common.yaml;

import lombok.extern.slf4j.Slf4j;

/**
 * Maybe multi threads read a variable
 *
 * @author Emmett Woo
 */
@Slf4j
public class YamlUtils {

    public static YamlData read(String configFileName) {
        return new YamlData(configFileName);
    }

}
