package com.ozf.laiyw.manage.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static final String DAYEND = " 23:59:59";
    public static final String YYYY = "yyyy";
    public static final String YYYY_MM = "yyyy-MM";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DDTHH_MM_SSXXX = "yyyy-MM-dd'T'HH:mm:ssXXX";
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String YYYYMMDD = "yyyyMMdd";

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd）
     */
    public static String getDate() {
        return getDate(YYYY_MM_DD);
    }

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String getDate(String pattern) {
        return new SimpleDateFormat(pattern).format(new Date());
    }

    /**
     * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String formatDate(Date date, Object... pattern) {
        String formatDate = null;
        if (pattern != null && pattern.length > 0) {
            formatDate = new SimpleDateFormat(pattern[0].toString()).format(date);
        } else {
            formatDate = new SimpleDateFormat(YYYY_MM_DD).format(date);
        }
        return formatDate;
    }

    /**
     * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String formatDateTime(Date date) {
        return formatDate(date, YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 得到当前时间字符串 格式（HH:mm:ss）
     */
    public static String getTime() {
        return formatDate(new Date(), "HH:mm:ss");
    }

    /**
     * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String getDateTime() {
        return formatDate(new Date(), YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 得到当前日期和时间字符串 格式（YYYYMMDDHHMMSS）
     */
    public static String getCurrentDateTime() {
        return formatDate(new Date(), YYYYMMDDHHMMSS);
    }

    /**
     * 得到当前年份字符串 格式（yyyy）
     */
    public static String getYear() {
        return formatDate(new Date(), "yyyy");
    }

    /**
     * 得到当前月份字符串 格式（MM）
     */
    public static String getMonth() {
        return formatDate(new Date(), "MM");
    }

    /**
     * 得到当天字符串 格式（dd）
     */
    public static String getDay() {
        return formatDate(new Date(), "dd");
    }

    /**
     * 得到当前星期字符串 格式（E）星期几
     */
    public static String getWeek() {
        return formatDate(new Date(), "E");
    }

    /**
     * 获取过去的天数
     *
     * @param date
     * @return
     */
    public static long pastDays(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (24 * 60 * 60 * 1000);
    }

    /**
     * 获取过去的小时
     *
     * @param date
     * @return
     */
    public static long pastHour(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (60 * 60 * 1000);
    }

    /**
     * 获取过去的分钟
     *
     * @param date
     * @return
     */
    public static long pastMinutes(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (60 * 1000);
    }

    /**
     * 两个时间点的分钟差
     *
     * @param before
     * @param after
     * @return
     */
    public static long pastMinutes(Date before, Date after) {
        long t = after.getTime() - before.getTime();
        return t / (60 * 1000);
    }

    /**
     * 转换为时间（天,时:分:秒.毫秒）
     *
     * @param timeMillis
     * @return
     */
    public static String formatDateTime(long timeMillis) {
        long day = timeMillis / (24 * 60 * 60 * 1000);
        long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
        long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
        return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "." + sss;
    }

    /**
     * 获取两个日期之间的天数
     *
     * @param before
     * @param after
     * @return
     */
    public static int getDistanceOfTwoDate(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (int) ((afterTime - beforeTime) / (1000 * 60 * 60 * 24));
    }

    /**
     * 将字符串时间转换为指定格式的日期时间
     *
     * @param strdate
     * @param pattern
     * @return
     */
    public static Date parseDate(String strdate, String pattern) {
        try {
            return new SimpleDateFormat(pattern).parse(strdate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获得指定月的开始时间，即2012-01-01 00:00:00
     *
     * @return
     */
    public static Date getMonthStartTime(String year_month) {
        Calendar c = Calendar.getInstance();
        c.setTime(parseDate(year_month, YYYY_MM));
        try {
            c.set(Calendar.DATE, 1);
            return parseDate(formatDate(c.getTime(), YYYY_MM_DD_HH_MM_SS), YYYY_MM_DD_HH_MM_SS);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取指定月的结束时间，即2012-01-31 23:59:59
     *
     * @return
     */
    public static Date getMonthEndTime(String year_month) {
        Calendar c = Calendar.getInstance();
        c.setTime(parseDate(year_month, YYYY_MM));
        try {
            c.set(Calendar.DATE, 1);
            c.add(Calendar.MONTH, 1);
            c.add(Calendar.DATE, -1);
            return parseDate(formatDate(c.getTime(), YYYY_MM_DD) + DAYEND, YYYY_MM_DD_HH_MM_SS);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取指定年的开始时间，即2012-01-01 00:00:00
     *
     * @return
     */
    public static Date getYearStartTime(String year) {
        Calendar c = Calendar.getInstance();
        c.setTime(parseDate(year, YYYY));
        try {
            c.set(Calendar.MONTH, 0);
            c.set(Calendar.DATE, 1);
            return parseDate(formatDate(c.getTime(), YYYY_MM_DD_HH_MM_SS), YYYY_MM_DD_HH_MM_SS);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取指定年的结束时间，即2012-12-31 23:59:59
     *
     * @return
     */
    public static Date getYearEndTime(String year) {
        Calendar c = Calendar.getInstance();
        c.setTime(parseDate(year, YYYY));
        try {
            c.set(Calendar.MONTH, 11);
            c.set(Calendar.DATE, 31);
            return parseDate(formatDate(c.getTime(), YYYY_MM_DD) + DAYEND, YYYY_MM_DD_HH_MM_SS);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取指定季度的开始时间，即2012-01-1 00:00:00
     *
     * @return
     */
    public static Date getQuarterStartTime(String year_quarter) {
        String yq[] = year_quarter.split("-");
        Calendar c = Calendar.getInstance();
        c.setTime(parseDate(yq[0], YYYY));
        int quarter = Integer.valueOf(yq[1]);
        try {
            if (quarter == 1)
                c.set(Calendar.MONTH, 0);
            else if (quarter == 2)
                c.set(Calendar.MONTH, 3);
            else if (quarter == 3)
                c.set(Calendar.MONTH, 6);
            else if (quarter == 4)
                c.set(Calendar.MONTH, 9);
            c.set(Calendar.DATE, 1);
            return parseDate(formatDate(c.getTime(), YYYY_MM_DD_HH_MM_SS), YYYY_MM_DD_HH_MM_SS);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取指定季度的结束时间，即2012-03-31 23:59:59
     *
     * @return
     */
    public static Date getQuarterEndTime(String year_quarter) {
        String yq[] = year_quarter.split("-");
        Calendar c = Calendar.getInstance();
        c.setTime(parseDate(yq[0], YYYY));
        int quarter = Integer.valueOf(yq[1]);
        try {
            if (quarter == 1) {
                c.set(Calendar.MONTH, 2);
                c.set(Calendar.DATE, 31);
            } else if (quarter == 2) {
                c.set(Calendar.MONTH, 5);
                c.set(Calendar.DATE, 30);
            } else if (quarter == 3) {
                c.set(Calendar.MONTH, 8);
                c.set(Calendar.DATE, 30);
            } else if (quarter == 4) {
                c.set(Calendar.MONTH, 11);
                c.set(Calendar.DATE, 31);
            }
            return parseDate(formatDate(c.getTime(), YYYY_MM_DD) + DAYEND, YYYY_MM_DD_HH_MM_SS);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 或者x小时之后的时间
     *
     * @param beginDate
     * @param duration
     * @return
     */
    public static Date countEndDate(Date beginDate, String duration) {
        Double time = Double.valueOf(duration.trim()) * 60 * 60 * 1000;
        return new Date(beginDate.getTime() + time.longValue());
    }

}
