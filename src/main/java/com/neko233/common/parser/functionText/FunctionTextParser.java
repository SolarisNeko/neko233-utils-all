package com.neko233.common.parser.functionText;

import lombok.extern.slf4j.Slf4j;
import com.neko233.common.base.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LuoHaoJun on 2022-12-27
 **/
@Slf4j
public class FunctionTextParser {


    /**
     * 解析函数文本
     *
     * @param content 函数内容. 例子: beRewardOnce(once,maxCount=999)
     */
    public static FunctionText parse(String content) {
        if (StringUtils.isBlank(content)) {
            return null;
        }

        String[] funcName2metadata = content.split("\\(|\\)");
        if (funcName2metadata.length != 2) {
            log.error("function content parse error. it format is error. content = {}", content);
            return null;
        }

        String functionName = "";
        final List<String> metadata = new ArrayList<>();
        final Map<String, String> kv = new HashMap<>();

        functionName = funcName2metadata[0];
        String properties = funcName2metadata[1];

        String[] keyValueOrTagArray = properties.split(",");
        for (String kvOrTag : keyValueOrTagArray) {
            String[] split = kvOrTag.split("=");
            if (split.length == 1) {
                metadata.add(split[0]);
                continue;
            }
            if (split.length == 2) {
                kv.put(split[0], split[1]);
                continue;
            }
            log.error("unknown what is this function condition. content = {}", kvOrTag);
        }

        return FunctionText.builder()
                .functionName(functionName)
                .metadata(metadata)
                .kv(kv)
                .build();
    }


}
