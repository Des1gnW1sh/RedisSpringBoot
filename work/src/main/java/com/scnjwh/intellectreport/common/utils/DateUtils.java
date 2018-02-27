/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">财政综合管理</a> All rights reserved.
 */
package com.scnjwh.intellectreport.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 *
 * @author ThinkGem
 * @version 2014-4-15
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    private static String[] parsePatterns = {"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss",
            "yyyy-MM-dd HH:mm", "yyyy-MM", "yyyy/MM/dd",
            "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss",
            "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd）
     */
    public static String getDate() {
        return getDate("yyyy-MM-dd");
    }

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String getDate(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }

    /**
     * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String formatDate(Date date, Object... pattern) {
        String formatDate = null;
        if (pattern != null && pattern.length > 0) {
            formatDate = DateFormatUtils.format(date, pattern[0].toString());
        } else {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
        }
        return formatDate;
    }

    /**
     * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String formatDateTime(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
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
        return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
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
     * 日期型字符串转化为日期 格式
     * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
     * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
     * "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取过去的天数
     *
     * @param date
     * @return
     */
    public static long pastDays(Date date) {
        long t = new Date().getTime() - date.getTime();
        return Math.abs(t / (24 * 60 * 60 * 1000));
    }

    /**
     * 获取过去的小时
     *
     * @param date
     * @return
     */
    public static long pastHour(Date date) {
        long t = new Date().getTime() - date.getTime();
        return Math.abs(t / (60 * 60 * 1000));
    }

    /**
     * 获取过去的分钟
     *
     * @param date
     * @return
     */
    public static long pastMinutes(Date date) {
        long t = new Date().getTime() - date.getTime();
        return Math.abs(t / (60 * 1000));
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
        long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60
                * 1000 - s * 1000);
        return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "." + sss;
    }

    /**
     * 获取两个日期之间的天数
     *
     * @param before
     * @param after
     * @return
     */
    public static double getDistanceOfTwoDate(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
    }

    /**
     * @param args
     * @throws ParseException
     */

    /**
     * 转换时间
     *
     * @param date Thu May 28 18:23:17 CST 2015
     * @return yyyy-MM-dd
     * @Author : pengjunhao. create at 2017年5月4日 上午11:42:35
     */
    public static String parseTsDate(Date date) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        String da = null;
        try {
            da = sdf2.format(sdf1.parse(date.toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return da;
    }

    /**
     * 比较两个日期的天数
     *
     * @param date1 日期1 yyyy-MM-dd
     * @param date2 日期2 yyyy-MM-dd
     * @return 0:相同 1: date1 大   -1：date2大
     * @Author : pengjunhao. create at 2017年5月4日 下午3:10:30
     */
    public static int compareDate(String date1, String date2) {
        String dateStr1 = date1.substring(0, 4) + date1.substring(5, 7) + date1.substring(8, 10);
        Integer it1 = Integer.valueOf(dateStr1);
        String dateStr2 = date2.substring(0, 4) + date2.substring(5, 7) + date2.substring(8, 10);
        Integer it2 = Integer.valueOf(dateStr2);
        if (it1 > it2) {
            //            System.out.println("dt1 在dt2前");
            return 1;
        } else if (it1 < it2) {
            //            System.out.println("dt1在dt2后");
            return -1;
        } else {//相等
            return 0;
        }
    }

    /**
     * 获取指定日期到现在日期的倒计时
     *
     * @param date   指定日期
     * @param isHtml 是否需要添加html节点
     * @return
     */
    public static String getCountDown(Date date, boolean isHtml) {
        long now = new Date().getTime();
        long dateLong = date.getTime();
        long surplus = Math.abs(dateLong - now);
        long day = surplus / (24 * 60 * 60 * 1000);
        long hour = (surplus - day * 24 * 60 * 60 * 1000) / (60 * 60 * 1000);
        long minute = (surplus - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000) / (60 * 1000);
        long seconds = (surplus - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - minute * 60 * 1000) / 1000;

        if (isHtml) {
            return "<span style='color:red'>" + day + "</span>天<span style='color:red'>" + (hour < 10 ? "0" + hour : hour) + "</span>时<span style='color:red'>" + (minute < 10 ? "0" + minute : minute) + "</span>分<span style='color:red'>" + (seconds < 10 ? "0" + seconds : seconds) + "</span>秒";
        } else {
            return day + "天" + (hour < 10 ? "0" + hour : hour) + "时" + (minute < 10 ? "0" + minute : minute) + "分" + (seconds < 10 ? "0" + seconds : seconds) + "秒";
        }
    }

    /**
     * 去掉长日期的秒数，去整分
     * @param date 需要处理的长日期
     * @return
     */
    public static Date delDateSeconds(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        try {
            String completeTime = format.format(date);
            String[] completeTimes = completeTime.split("分");
            completeTime = completeTimes[0] + "分00秒";
            return format.parse(completeTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void main(String[] args) {
        try {
            System.out.println("==" + pastHour(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-1-13 10:30:00")));
            System.out.println("==" + getCountDown(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-1-13 10:30:00"), true));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
