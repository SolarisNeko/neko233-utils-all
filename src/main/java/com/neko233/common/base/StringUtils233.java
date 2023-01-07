package com.neko233.common.base;


import java.util.Locale;

public class StringUtils233 {

    private StringUtils233() {
    }

    /**
     * 没找到的 index
     */
    public static final int INDEX_NOT_FOUND = -1;

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
     * 最大处理字符串
     */
    private static final int MAX_HANDLE_STRING = 8192;


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

}
