package com.powernode.crm.commons.utils;

/**
 * @title:DateUtils Author liu
 * @Date:2023/3/29 16:33
 * @Version 1.0
 */

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 对日期进行处理的工具类
 */
public class DateUtils {
    /**
     * 对指定的date对象进行格式化：yyyy-MM-dd HH:mm:ss，包含年月日，时分秒
     * @param date
     * @return
     */
    public static String formatDateTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdf.format(date);
        return dateStr;
    }
    /**
     * 对指定的date对象进行格式化：yyyy-MM-dd，只包含年月日
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(date);
        return dateStr;
    }

    /**
     * 对指定的date对象进行格式化：HH:mm:ss，只包含时分秒
     * @param date
     * @return
     */
    public static String formatTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String dateStr = sdf.format(date);
        return dateStr;
    }
}
