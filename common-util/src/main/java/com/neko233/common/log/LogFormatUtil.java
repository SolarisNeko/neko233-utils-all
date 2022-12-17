package com.neko233.common.log;

import com.neko233.common.regex.RegexUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 日志模板解析工具
 * 以 line 为单位
 *
 * @author SolarisNeko
 * Date on 2022-12-17
 */
@Slf4j
public class LogFormatUtil {

    /**
     * 占位符格式. 格式 = ${英文+数字}
     */
    private static final String PLACEHOLDER_KEY_REGEX = "\\$\\{([a-z]|[A-Z]|[0-9])+\\}";

    /**
     * 文件名
     */
    public static List<Map<String, String>> formatLogFile(String fileName, String logFormat) throws IOException {
        return formatLogFile(new File(fileName), logFormat);
    }

    /**
     * 文件
     *
     * @param line      一行
     * @param logFormat 自定义的日志格式. 需要提供占位 key = ${fieldName}
     * @return 解析的结果, list 中对应每一行
     */
    public static Map<String, String> formatLine(String line, String logFormat) {
        return lineFormatMapping(line, logFormat);
    }


    public static List<Map<String, String>> formatLogFile(File logFile, String logFormat) throws IOException {
        if (!logFile.exists()) {
            return new ArrayList<>();
        }

        final List<String> keywordTemplates = getKeywordTemplates(logFormat);
        final String toSplitRegexExpression = generateRegexSplitExpression(logFormat, keywordTemplates);

        List<String> lines = FileUtils.readLines(logFile, StandardCharsets.UTF_8);
        return lines.stream()
                .map(line -> LogFormatUtil.lineFormatMapping(line, keywordTemplates, toSplitRegexExpression))
                .collect(Collectors.toList());
    }


    private static Map<String, String> lineFormatMapping(String line, String logFormat) {
        final List<String> keywordTemplates = getKeywordTemplates(logFormat);
        final String toSplitRegexExpression = generateRegexSplitExpression(logFormat, keywordTemplates);

        return lineFormatMapping(line, keywordTemplates, toSplitRegexExpression);
    }

    /**
     * @param line                   一行数据
     * @param keywordTemplateList    占位符模版 = ${a}, ${b}
     * @param toSplitRegexExpression 切割每一行的正则表达式.
     * @return 根据 logFormat, 自动解析 line, 返回映射结果 Map
     */
    public static Map<String, String> lineFormatMapping(String line, List<String> keywordTemplateList, String toSplitRegexExpression) {
        if (StringUtils.isBlank(line)) {
            return new HashMap<>(0);
        }

        List<String> contentList = Arrays.asList(line.split(toSplitRegexExpression));

        List<String> keywordList = keywordTemplateList.stream()
                .map(LogFormatUtil::getKeywordFromPlaceHolder)
                .collect(Collectors.toList());

        // 生成映射结果
        Map<String, String> resultMap = new HashMap<>();

        for (int start = 0; start < contentList.size(); start++) {
            String content = contentList.get(start);
            if (StringUtils.isBlank(content)) {
                continue;
            }

            // 索引超出部分, 追加到最后
            if (start > (keywordList.size() - 1)) {
                resultMap.merge(
                        keywordList.get(keywordList.size() - 1),
                        content,
                        (v1, v2) -> v1 + v2
                );
                continue;
            }
            resultMap.put(keywordList.get(start), content);
        }
        return resultMap;
    }

    private static String getKeywordFromPlaceHolder(String placeholder) {
        return placeholder
                .replace("${", "")
                .replace("}", "");
    }

    @NotNull
    private static String generateRegexSplitExpression(String logFormat, List<String> keywordTemplates) {
        if (StringUtils.isBlank(logFormat)) {
            return "";
        }
        String newRegexList = Optional.ofNullable(keywordTemplates)
                .orElse(new ArrayList<>())
                .stream()
                .map(RegexUtil::convertOriginalRegex)
                .collect(Collectors.joining("|"));
        List<String> clearanceList = Arrays.stream(logFormat.split(newRegexList))
                .filter(StringUtils::isNotEmpty)
                .distinct()
                .sorted(regexSorting())
                .collect(Collectors.toList());
        return String.join("|", clearanceList);
    }

    /**
     * @param logFormat 日志格式
     * @return sort list 有序关键词
     */
    @NotNull
    private static List<String> getKeywordTemplates(String logFormat) {
        List<String> keywordTemplates = new ArrayList<>();
        Pattern compile = Pattern.compile(PLACEHOLDER_KEY_REGEX);
        Matcher matcher = compile.matcher(logFormat);
        while (matcher.find()) {
            String keywordTemplate = matcher.group();
            keywordTemplates.add(keywordTemplate);
        }
        return keywordTemplates;
    }

    /**
     * @return 正则顺序.
     */
    @NotNull
    private static Comparator<String> regexSorting() {
        return (o1, o2) -> {
            if (" ".equals(o1)) {
                return 1;
            }
            if (" ".equals(o2)) {
                return -1;
            }
            return 0;
        };
    }


}
