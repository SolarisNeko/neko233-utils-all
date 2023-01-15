package com.neko233.common.base;

import org.joda.time.DateTime;

import java.time.LocalDateTime;
import java.time.Year;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

/**
 * 优先以 java-8 的 java.time 为标准, 该日期工具主要做补充 & 方便
 *
 * @author SolarisNeko
 */
public class DateTimeUtils233 {

    /**
     * 是否闰年
     *
     * @param year 年
     * @return 是否闰年. 2月 29 日
     */
    public static boolean isLeapYear(int year) {
        return Year.isLeap(year);
    }


    /**
     * 当前时间，转换为{@link DateTime}对象
     *
     * @return 当前时间
     */
    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    /**
     * 解析时间戳 text
     *
     * @param datetimeText 文本
     * @param pattern      格式
     * @return LocalDateTime
     */
    public static LocalDateTime parse(String datetimeText, String pattern) {
        return LocalDateTime.parse(datetimeText, DateTimeFormatter.ofPattern(pattern));
    }

    public static long getMs(LocalDateTime dateTime) {
        return dateTime.toEpochSecond(ZoneOffset.of(ZoneOffset.systemDefault().getId()));
    }

    public static long getMs(LocalDateTime dateTime, ZoneOffset zoneOffset) {
        return dateTime.toEpochSecond(zoneOffset);
    }

    /**
     * 计算相对于dateToCompare的年龄，长用于计算指定生日在某年的年龄
     *
     * @param birthdayDt 生日
     * @param otherDt    需要对比的日期
     * @return 年龄
     */
    public static int age(LocalDateTime birthdayDt, LocalDateTime otherDt) {
        AssertUtils233.isNotNull(birthdayDt, "Birthday can not be null !");
        if (otherDt == null) {
            otherDt = now();
        }
        return betweenAge(getMs(birthdayDt), getMs(otherDt));
    }


    /**
     * 计算相对于dateToCompare的年龄，长用于计算指定生日在某年的年龄
     *
     * @param birthday      生日
     * @param dateToCompare 需要对比的日期
     * @return 年龄
     */
    protected static int betweenAge(long birthday, long dateToCompare) {
        if (birthday > dateToCompare) {
            throw new IllegalArgumentException("Birthday is after dateToCompare!");
        }

        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(dateToCompare);

        final int year = cal.get(Calendar.YEAR);
        final int month = cal.get(Calendar.MONTH);
        final int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        final boolean isLastDayOfMonth = dayOfMonth == cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        cal.setTimeInMillis(birthday);
        int age = year - cal.get(Calendar.YEAR);

        final int monthBirth = cal.get(Calendar.MONTH);
        if (month == monthBirth) {

            final int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
            final boolean isLastDayOfMonthBirth = dayOfMonthBirth == cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            if ((false == isLastDayOfMonth || false == isLastDayOfMonthBirth) && dayOfMonth < dayOfMonthBirth) {
                // 如果生日在当月，但是未达到生日当天的日期，年龄减一
                age--;
            }
        } else if (month < monthBirth) {
            // 如果当前月份未达到生日的月份，年龄计算减一
            age--;
        }

        return age;
    }


}
