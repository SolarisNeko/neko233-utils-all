package com.neko233.common.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author SolarisNeko
 * Date on 2022-12-17
 */
public class RegexUtil {

    /**
     * 获得匹配正则表达式的内容
     *
     * @param allContent        字符串
     * @param regex             正则表达式
     * @param isCaseInsensitive 是否忽略大小写，true忽略大小写，false大小写敏感
     * @return 匹配正则表达式的字符串，组成的List
     */
    public static List<String> getMatchList(final String allContent,
                                            final String regex,
                                            final boolean isCaseInsensitive) {
        List<String> result = new ArrayList<>();
        Pattern pattern = null;
        // 是否大小写敏感
        if (isCaseInsensitive) {
            pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        } else {
            pattern = Pattern.compile(regex);
        }
        // 指定要匹配的字符串
        Matcher matcher = pattern.matcher(allContent);
        // 此处find（）每次被调用后，会偏移到下一个匹配
        while (matcher.find()) {
            // 获取当前匹配的值
            result.add(matcher.group());
        }
        return result;
    }

    /**
     * 转义原生正则 -> Java 转义处理
     *
     * @param originalRegex 原生正则
     * @return 处理后的正则
     */
    public static String convertOriginalRegex(String originalRegex) {
        return originalRegex.replace("$", "\\$")
                .replace("{", "\\{")
                .replace("}", "\\}")
                .replace("-", "\\-")
                ;
    }

}
