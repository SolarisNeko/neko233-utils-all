package com.neko233.common.base;


import com.neko233.validation.annotation.Nullable;
import org.apache.commons.collections4.CollectionUtils;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class StringUtils233 {



    private StringUtils233() {
    }

    /**
     * 没找到的 index
     */
    public static final int INDEX_NOT_FOUND = -1;

    /**
     * char 空格
     */
    public static final char C_SPACE = ' ';

    /**
     * 空格
     */
    public static final String SPACE = " ";

    /**
     * 空字符串
     */
    public static final String EMPTY = "";

    /**
     * A String for linefeed LF ("\n").
     * 换行
     */
    public static final String LF = "\n";

    /**
     * carriage return CR ("\r").
     * 回车
     */
    public static final String CR = "\r";

    /**
     * 空数组
     */
    private static final String[] EMPTY_STRING_ARRAY = new String[0];

    /**
     * 最大处理字符串
     */
    private static final int MAX_HANDLE_STRING = 8192;

    /**
     * 字符常量：斜杠 {@code '/'}
     */
    private static final char C_SLASH = '/';

    /**
     * 字符常量：反斜杠 {@code '\\'}
     */
    private static final char C_BACKSLASH = '\\';

    /**
     * 字符串常量：空 JSON {@code "{}"}
     */
    private static final String EMPTY_JSON = "{}";


    public static boolean isEmpty(final char... cs) {
        return isEmpty(new String(cs));
    }

    /**
     * 空字符串判断
     */
    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotEmpty(final CharSequence cs) {
        return !isEmpty(cs);
    }

    public static boolean isBlank(final CharSequence cs) {
        final int strLen = length(cs);
        if (strLen == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>Checks if a CharSequence is not empty (""), not null and not whitespace only.</p>
     *
     * <p>Whitespace is defined by {@link Character#isWhitespace(char)}.</p>
     *
     * <pre>
     * StringUtils.isNotBlank(null)      = false
     * StringUtils.isNotBlank("")        = false
     * StringUtils.isNotBlank(" ")       = false
     * StringUtils.isNotBlank("bob")     = true
     * StringUtils.isNotBlank("  bob  ") = true
     * </pre>
     *
     * @param cs the CharSequence to check, may be null
     * @since 0.1.8
     */
    public static boolean isNotBlank(final CharSequence cs) {
        return !isBlank(cs);
    }

    public static int length(final CharSequence cs) {
        return cs == null ? 0 : cs.length();
    }

    public static String truncate(final String str, final int maxWidth) {
        return truncate(str, 0, maxWidth);
    }

    public static String truncate(final String str, final int offset, final int maxWidth) {
        if (offset < 0) {
            throw new IllegalArgumentException("offset cannot be negative");
        }
        if (maxWidth < 0) {
            throw new IllegalArgumentException("maxWith cannot be negative");
        }
        if (str == null) {
            return null;
        }
        if (offset > str.length()) {
            return EMPTY;
        }
        if (str.length() > maxWidth) {
            final int ix = Math.min(offset + maxWidth, str.length());
            return str.substring(offset, ix);
        }
        return str.substring(offset);
    }

    /**
     * not null upper case!
     */
    public static String upperCase(final String str) {
        return upperCase(str, null);
    }

    public static String upperCase(final String str, final Locale locale) {
        if (str == null) {
            return null;
        }
        if (locale == null) {
            return str.toUpperCase();
        }
        return str.toUpperCase(locale);
    }

    public static String lowerCase(final String str) {
        return lowerCase(str, null);
    }

    public static String lowerCase(final String str, final Locale locale) {
        if (str == null) {
            return null;
        }
        if (locale == null) {
            return str.toLowerCase();
        }
        return str.toLowerCase(locale);
    }


    /**
     * find some data between [openStr, closeStr]
     * maybe null
     *
     * <pre>
     * StringUtils.substringBetween("wx[b]yz", "[", "]") = "b"
     * StringUtils.substringBetween(null, *, *)          = null
     * StringUtils.substringBetween(*, null, *)          = null
     * StringUtils.substringBetween(*, *, null)          = null
     * StringUtils.substringBetween("", "", "")          = ""
     * StringUtils.substringBetween("", "", "]")         = null
     * StringUtils.substringBetween("", "[", "]")        = null
     * StringUtils.substringBetween("yabcz", "", "")     = ""
     * StringUtils.substringBetween("yabcz", "y", "z")   = "abc"
     * StringUtils.substringBetween("yabczyabcz", "y", "z")   = "abc"
     * </pre>
     *
     * @param targetString the String containing the substring, may be null
     * @param openStr      the String before the substring, may be null
     * @param closeStr     the String after the substring, may be null
     * @return the substring, {@code null} if no match
     * @since 2.0
     */
    public static String substringBetween(final String targetString, final String openStr, final String closeStr) {
        if (ObjectUtils233.isAnyNull(targetString, openStr, closeStr)) {
            return null;
        }
        final int start = targetString.indexOf(openStr);
        if (start != INDEX_NOT_FOUND) {
            final int end = targetString.indexOf(closeStr, start + openStr.length());
            if (end != INDEX_NOT_FOUND) {
                return targetString.substring(start + openStr.length(), end);
            }
        }
        return null;
    }


    /**
     * "1, 2, 3" --> "1,2,3"
     *
     * @param s         文本
     * @param separator 分隔符
     * @return trim 后的内容
     */
    public static String splitTrimThenJoin(String s, String separator) {
        String notNullStr = Optional.of(s).orElse("");
        return Arrays.stream(notNullStr.split(separator))
                .map(String::trim)
                .filter(StringUtils233::isNotBlank)
                .collect(Collectors.joining(separator));
    }

    /**
     * JDK 默认的 join 不方便
     */
    public static String join(CharSequence delimiter, Object... elements) {
        Objects.requireNonNull(delimiter);
        Objects.requireNonNull(elements);
        // Number of elements not likely worth Arrays.stream overhead.
        StringJoiner joiner = new StringJoiner(delimiter);
        for (Object obj : elements) {
            joiner.add(String.valueOf(obj));
        }
        return joiner.toString();
    }


    public static String[] tokenizeToStringArray(@Nullable String str, String delimiters) {
        return tokenizeToStringArray(str, delimiters, true, true);
    }

    /**
     * split & trim & ignore null token
     *
     * @param str               内容
     * @param delimiters        分隔符
     * @param trimTokens        trim ?
     * @param ignoreEmptyTokens ignore empty?
     * @return String
     */
    public static String[] tokenizeToStringArray(@Nullable String str, String delimiters, boolean trimTokens, boolean ignoreEmptyTokens) {
        if (str == null) {
            return new String[]{};
        } else {
            StringTokenizer st = new StringTokenizer(str, delimiters);
            List<String> tokens = new ArrayList<>();

            while (true) {
                String token;
                do {
                    if (!st.hasMoreTokens()) {
                        return toStringArray(tokens);
                    }

                    token = st.nextToken();
                    if (trimTokens) {
                        token = token.trim();
                    }
                } while (ignoreEmptyTokens && token.length() <= 0);

                tokens.add(token);
            }
        }
    }

    public static String[] toStringArray(@Nullable Collection<String> collection) {
        return CollectionUtils.isNotEmpty(collection) ? collection.toArray(EMPTY_STRING_ARRAY) : EMPTY_STRING_ARRAY;
    }

    public static void appendByPrintStyle(StringBuilder builder, String key, Object value) {
        builder.append(key)
                .append(value)
                .append("\n");
    }


    public static String format(String strPattern, Object... argArray) {
        return format(strPattern, EMPTY_JSON, argArray);
    }

    /**
     * 格式化字符串<br>
     * 此方法只是简单将指定占位符 按照顺序替换为参数<br>
     * 如果想输出占位符使用 \\转义即可，如果想输出占位符之前的 \ 使用双转义符 \\\\ 即可<br>
     * 例：<br>
     * 通常使用：format("this is {} for {}", "{}", "a", "b") =》 this is a for b<br>
     * 转义{}： format("this is \\{} for {}", "{}", "a", "b") =》 this is {} for a<br>
     * 转义\： format("this is \\\\{} for {}", "{}", "a", "b") =》 this is \a for b<br>
     *
     * @param strPattern  字符串模板
     * @param placeHolder 占位符，例如{}
     * @param argArray    参数列表
     * @return 结果
     */
    public static String format(String strPattern, String placeHolder, Object... argArray) {
        if (StringUtils233.isBlank(strPattern) || StringUtils233.isBlank(placeHolder) || ArrayUtils233.isEmpty(argArray)) {
            return strPattern;
        }
        final int strPatternLength = strPattern.length();
        final int placeHolderLength = placeHolder.length();

        // 初始化定义好的长度以获得更好的性能
        final StringBuilder sbuf = new StringBuilder(strPatternLength + 50);

        int handledPosition = 0;// 记录已经处理到的位置
        int delimIndex;// 占位符所在位置
        for (int argIndex = 0; argIndex < argArray.length; argIndex++) {
            delimIndex = strPattern.indexOf(placeHolder, handledPosition);
            if (delimIndex == -1) {// 剩余部分无占位符
                if (handledPosition == 0) { // 不带占位符的模板直接返回
                    return strPattern;
                }
                // 字符串模板剩余部分不再包含占位符，加入剩余部分后返回结果
                sbuf.append(strPattern, handledPosition, strPatternLength);
                return sbuf.toString();
            }

            // 转义符
            if (delimIndex > 0 && strPattern.charAt(delimIndex - 1) == StringUtils233.C_BACKSLASH) {// 转义符
                if (delimIndex > 1 && strPattern.charAt(delimIndex - 2) == StringUtils233.C_BACKSLASH) {// 双转义符
                    // 转义符之前还有一个转义符，占位符依旧有效
                    sbuf.append(strPattern, handledPosition, delimIndex - 1);
                    sbuf.append(String.valueOf(argArray[argIndex]));
                    handledPosition = delimIndex + placeHolderLength;
                } else {
                    // 占位符被转义
                    argIndex--;
                    sbuf.append(strPattern, handledPosition, delimIndex - 1);
                    sbuf.append(placeHolder.charAt(0));
                    handledPosition = delimIndex + 1;
                }
            } else {// 正常占位符
                sbuf.append(strPattern, handledPosition, delimIndex);
                sbuf.append(StringUtils233.toUtf8String(argArray[argIndex]));
                handledPosition = delimIndex + placeHolderLength;
            }
        }

        // 加入最后一个占位符后所有的字符
        sbuf.append(strPattern, handledPosition, strPatternLength);

        return sbuf.toString();
    }

    /**
     * 将对象转为字符串<br>
     *
     * <pre>
     * 1、Byte数组和ByteBuffer会被转换为对应字符串的数组
     * 2、对象数组会调用Arrays.toString方法
     * </pre>
     *
     * @param obj 对象
     * @return 字符串
     */
    public static String toUtf8String(Object obj) {
        return stringIfNull(obj, StandardCharsets.UTF_8);
    }

    /**
     * 将对象转为字符串
     * <pre>
     * 	 1、Byte数组和ByteBuffer会被转换为对应字符串的数组
     * 	 2、对象数组会调用Arrays.toString方法
     * </pre>
     *
     * @param obj     对象
     * @param charset 字符集
     * @return 字符串
     */
    public static String stringIfNull(Object obj, Charset charset) {
        if (null == obj) {
            return null;
        }

        if (obj instanceof String) {
            return (String) obj;
        } else if (obj instanceof byte[]) {
            return stringIfNull((byte[]) obj, charset);
        } else if (obj instanceof Byte[]) {
            return stringIfNull((Byte[]) obj, charset);
        } else if (obj instanceof ByteBuffer) {
            return stringIfNull((ByteBuffer) obj, charset);
        } else if (ArrayUtils233.isArray(obj)) {
            return ArrayUtils233.toString(obj);
        }

        return obj.toString();
    }

    /**
     * 去除字符串中指定的多个字符，如有多个则全部去除
     *
     * @param str   字符串
     * @param chars 字符列表
     * @return 去除后的字符
     * @since 4.2.2
     */
    public static String removeAll(CharSequence str, char... chars) {
        if (str == null) {
            return null;
        }
        if (isEmpty(chars)) {
            return str.toString();
        }
        final int len = str.length();
        if (0 == len) {
            return str.toString();
        }
        final StringBuilder builder = new StringBuilder(len);
        char c;
        for (int i = 0; i < len; i++) {
            c = str.charAt(i);
            if (containsChar(chars, c)) {
                continue;
            }
            builder.append(c);
        }
        return builder.toString();
    }

    /**
     * 数组中是否包含元素
     *
     * @param chars 字符数组
     * @param value 被检查的元素
     * @return 是否包含
     */
    private static boolean containsChar(char[] chars, char value) {
        if (null != chars) {
            for (int i = 0; i < chars.length; i++) {
                if (value == chars[i]) {
                    return true;
                }
            }
        }
        return false;
    }

    public static List<String> splitTrim(String str, String split) {
        if (str == null) {
            return new ArrayList<>();
        }
        return Arrays.stream(str.split(split))
                .map(String::trim)
                .collect(Collectors.toList());
    }

    /**
     * public static String subPre(String startIp, int lastDotIndex) {
     * 切割指定位置之前部分的字符串
     *
     * @param string         字符串
     * @param subPreIndex 切割到的位置（不包括）
     * @return 切割后的剩余的前半部分字符串
     */
    public static String subPre(CharSequence string, int subPreIndex) {
        return sub(string, 0, subPreIndex);
    }


    /**
     * 改进JDK subString<br>
     * index从0开始计算，最后一个字符为-1<br>
     * 如果from和to位置一样，返回 "" <br>
     * 如果from或to为负数，则按照length从后向前数位置，如果绝对值大于字符串长度，则from归到0，to归到length<br>
     * 如果经过修正的index中from大于to，则互换from和to example: <br>
     * abcdefgh 2 3 =》 c <br>
     * abcdefgh 2 -3 =》 cde <br>
     *
     * @param str              String
     * @param fromIndexInclude 开始的index（包括）
     * @param toIndexExclude   结束的index（不包括）
     * @return 字串
     */
    public static String sub(CharSequence str, int fromIndexInclude, int toIndexExclude) {
        if (isEmpty(str)) {
            return stringIfNull(str);
        }
        int len = str.length();

        if (fromIndexInclude < 0) {
            fromIndexInclude = len + fromIndexInclude;
            if (fromIndexInclude < 0) {
                fromIndexInclude = 0;
            }
        } else if (fromIndexInclude > len) {
            fromIndexInclude = len;
        }

        if (toIndexExclude < 0) {
            toIndexExclude = len + toIndexExclude;
            if (toIndexExclude < 0) {
                toIndexExclude = len;
            }
        } else if (toIndexExclude > len) {
            toIndexExclude = len;
        }

        if (toIndexExclude < fromIndexInclude) {
            int tmp = fromIndexInclude;
            fromIndexInclude = toIndexExclude;
            toIndexExclude = tmp;
        }

        if (fromIndexInclude == toIndexExclude) {
            return EMPTY;
        }

        return str.toString().substring(fromIndexInclude, toIndexExclude);
    }

    /**
     * {@link CharSequence} 转为字符串，null安全
     *
     * @param cs {@link CharSequence}
     * @return 字符串
     */
    public static String stringIfNull(CharSequence cs) {
        return null == cs ? null : cs.toString();
    }

    /**
     * 切割指定位置之后部分的字符串
     *
     * @param string    字符串
     * @param fromIndex 切割开始的位置（包括）
     * @return 切割后后剩余的后半部分字符串
     */
    public static String subSuf(CharSequence string, int fromIndex) {
        if (isEmpty(string)) {
            return null;
        }
        return sub(string, fromIndex, string.length());
    }

    /**
     * 替换指定字符串的指定区间内字符为"*"
     * 俗称：脱敏功能，后面其他功能，可以见：DesensitizedUtil(脱敏工具类)
     *
     * <pre>
     * StrUtil.hide(null,*,*)=null
     * StrUtil.hide("",0,*)=""
     * StrUtil.hide("jackduan@163.com",-1,4)   ****duan@163.com
     * StrUtil.hide("jackduan@163.com",2,3)    ja*kduan@163.com
     * StrUtil.hide("jackduan@163.com",3,2)    jackduan@163.com
     * StrUtil.hide("jackduan@163.com",16,16)  jackduan@163.com
     * StrUtil.hide("jackduan@163.com",16,17)  jackduan@163.com
     * </pre>
     *
     * @param str          字符串
     * @param startInclude 开始位置（包含）
     * @param endExclude   结束位置（不包含）
     * @return 替换后的字符串
     * @since 4.1.14
     */
    public static String hide(CharSequence str, int startInclude, int endExclude) {
        return replaceEx(str, startInclude, endExclude, '*');
    }

    /**
     * 替换指定字符串的指定区间内字符为固定字符<br>
     * 此方法使用{@link String#codePoints()}完成拆分替换
     *
     * @param str          字符串
     * @param startInclude 开始位置（包含）
     * @param endExclude   结束位置（不包含）
     * @param replacedChar 被替换的字符
     * @return 替换后的字符串
     * @since 3.2.1
     */
    public static String replaceEx(CharSequence str, int startInclude, int endExclude, char replacedChar) {
        if (isEmpty(str)) {
            return stringIfNull(str);
        }
        final String originalStr = stringIfNull(str);
        int[] strCodePoints = originalStr.codePoints().toArray();
        final int strLength = strCodePoints.length;
        if (startInclude > strLength) {
            return originalStr;
        }
        if (endExclude > strLength) {
            endExclude = strLength;
        }
        if (startInclude > endExclude) {
            // 如果起始位置大于结束位置，不替换
            return originalStr;
        }

        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < strLength; i++) {
            if (i >= startInclude && i < endExclude) {
                stringBuilder.append(replacedChar);
            } else {
                stringBuilder.append(new String(strCodePoints, i, 1));
            }
        }
        return stringBuilder.toString();
    }

}
