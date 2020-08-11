package com.hand.hls.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author zhengying
 */
public class DateUtils extends org.apache.commons.lang.time.DateUtils {

    private static Calendar startDate = Calendar.getInstance();
    private static Calendar endDate = Calendar.getInstance();

    public static Date sysdate() {
        return new Date();
    }

    public static String sysPeriod() {
        String formatStr = "yyyy-MM";
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatStr);
        String sysPeriod = dateFormat.format(DateUtils.sysdate());
        return sysPeriod;
    }

    public static String date2Str(Date date, String formatStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        return sdf.format(date);
    }

    public static Date str2Date(String str, String formatStr) {
        DateFormat dateFormat = new SimpleDateFormat(formatStr);
        Date date = null;
        try {
            date = dateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static Date fromatDate(Date pdate, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String s = sdf.format(pdate);
        Date date = null;
        try {
            date =  sdf.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date dateAddDays(Date date, Integer day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, day);
        return calendar.getTime();
    }

    public static Date dateAddMonths(Date date, Integer month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }


    /**
     * 计算两个时间相差多少个年
     *
     * @param start
     * @param end
     * @return
     * @throws ParseException
     */
    public static int yearsBetween(Date start, Date end) {
        startDate.setTime(start);
        endDate.setTime(end);
        return (endDate.get(Calendar.YEAR) - startDate.get(Calendar.YEAR));
    }


    /**
     * 计算两个时间相差多少个月
     *
     * @param start
     *            <String>
     * @param end
     *            <String>
     * @return int
     * @throws ParseException
     */
    public static int monthsBetween(Date start, Date end) {
        startDate.setTime(start);
        endDate.setTime(end);
        int result = yearsBetween(start, end) * 12 + endDate.get(Calendar.MONTH) - startDate.get(Calendar.MONTH);
        return Math.abs(result);

    }
    /**
     * 计算两个时间相差多少个天
     *
     * @param start
     * @param end
     * @return
     * @throws ParseException
     */
    public static int daysBetween(Date start, Date end) {
        // 得到两个日期相差多少天
        return hoursBetween(start, end) / 24;
    }

    /**
     * 计算两个时间相差多少小时
     *
     * @param start
     * @param end
     * @return
     * @throws ParseException
     */
    public static int hoursBetween(Date start, Date end) {
        // 得到两个日期相差多少小时
        return minutesBetween(start, end) / 60;
    }

    /**
     * 计算两个时间相差多少分
     *
     * @param start
     * @param end
     * @return
     * @throws ParseException
     */
    public static int minutesBetween(Date start, Date end) {
        // 得到两个日期相差多少分
        return secondesBetween(start, end) / 60;
    }

    /**
     * 计算两个时间相差多少秒
     *
     * @param start
     * @param end
     * @return
     * @throws ParseException
     */
    public static int secondesBetween(Date start, Date end) {
        Date earlydate = start;
        Date latedate = end;
        startDate.setTime(earlydate);
        endDate.setTime(latedate);
        // 设置时间为0时
        startDate.set(Calendar.HOUR_OF_DAY, 0);
        startDate.set(Calendar.MINUTE, 0);
        startDate.set(Calendar.SECOND, 0);
        endDate.set(Calendar.HOUR_OF_DAY, 0);
        endDate.set(Calendar.MINUTE, 0);
        endDate.set(Calendar.SECOND, 0);
        // 得到两个日期相差多少秒
        return ((int) (endDate.getTime().getTime() / 1000) - (int) (startDate.getTime().getTime() / 1000));
    }
}
