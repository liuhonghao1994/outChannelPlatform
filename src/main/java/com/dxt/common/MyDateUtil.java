package com.dxt.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MyDateUtil {
    
    private static final Logger logger = LoggerFactory.getLogger(MyDateUtil.class);

    public enum FormatPattern {
        YYYY_MM_DD_HH_MM_SS_0("yyyy-MM-dd HH:mm:ss.0"),
        YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss"),
        YYYYMMDDHHMMSS("yyyyMMddHHmmss"),
        YYYY_Y_MM_M_DD_D("yyyy年MM月dd日"),
        YYYY_MM_DD("yyyy-MM-dd"),
        YYYYMMDD("yyyyMMdd"),
        YYYYMM("yyyyMM"),
        MMYYYY("MMyyyy");

        private String formatPattern;

        FormatPattern(String formatPattern) {
            this.formatPattern = formatPattern;
        }

        public String getFormatPattern() {
            return formatPattern;
        }

        private void setFormatPattern(String formatPattern) {
            this.formatPattern = formatPattern;
        }
    }

    /**
     * 通过入参字符串（年月）得到日期
     * @param dateStr
     * @return
     */
    public static Date getDateFromStringFormat(String dateStr, String formatPattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatPattern);
        Date date = null;
        try {
            date = simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            logger.error(LogHelper._FUNC_() + e.getMessage());
        }
        return date;
    }

    /**
     * 根据日期得到当月第一天的日期字符串
     * @param date
     * @return
     */
    public static String getMonthFirstDateStringFromDate(Date date, String formatPattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatPattern);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //设置为1号,当前日期既为本月第一天
        calendar.set(Calendar.DAY_OF_MONTH,1);
        Date endDate = calendar.getTime();
        return simpleDateFormat.format(endDate);
    }

    /**
     * 根据日期得到当月最后一天的日期字符串
     * @param date
     * @return
     */
    public static String getMonthLastDateStringFromDate(Date date, String formatPattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatPattern);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //月增加1
        calendar.add(Calendar.MONTH,1);
        //日期倒数一日,既得到本月最后一天
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        Date endDate = calendar.getTime();
        return simpleDateFormat.format(endDate);
    }

    /**
     * 根据日期得到当月第一天的日期字符串
     * @param date
     * @return
     */
    public static String getMonthFirstDateStringFromDate(Date date, String formatPattern, int addMonth) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatPattern);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, addMonth);
        //设置为1号,当前日期既为本月第一天
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date endDate = calendar.getTime();
        return simpleDateFormat.format(endDate);
    }

    /**
     * 根据日期得到当月最后一天的日期字符串
     * @param date
     * @return
     */
    public static String getMonthLastDateStringFromDate(Date date, String formatPattern, int addMonth) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatPattern);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //月增加1
        ++addMonth;
        calendar.add(Calendar.MONTH, addMonth);
        //日期倒数一日,既得到本月最后一天
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        Date endDate = calendar.getTime();
        return simpleDateFormat.format(endDate);
    }

    /**
     * 根据日期得到字符串，格式为
     * @param date
     * @return
     */
    public static String getDateStringFromDate(Date date, String formatPattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatPattern);
        return simpleDateFormat.format(date);
    }


    /**
     * 根据其他日期字符串得到当月字符串
     * @param dateStr
     * @return
     */
    public static String getDateStringFromOtherFormatDateStr(String dateStr, String formatPattern) {
        Date date = getDateFromString(dateStr);
        if (null == date) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatPattern);
        return simpleDateFormat.format(date);
    }

    /**
     * 不指定日期字符串格式，获取时间
     * @param dateStr
     * @return
     */
    public static Date getDateFromString(String dateStr) {
        if (null == dateStr || "".equals(dateStr)) {
            return null;
        }
        Date date = null;
        for (FormatPattern formatPattern : FormatPattern.values()) {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatPattern.getFormatPattern());
                simpleDateFormat.setLenient(false);
                date = simpleDateFormat.parse(dateStr);
                return date;
            } catch (ParseException e) {
                date = null;
            }
        }
        return date;
    }

    /**
     * 指定日期字符串格式，获取时间
     * @param dateStr
     * @return
     */
    public static Date getDateFromStringWithFormatPattern(String dateStr, String formatPattern) {
        if (null == dateStr || "".equals(dateStr)) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatPattern);
        try {
            Date date = simpleDateFormat.parse(dateStr);
            return date;
        } catch (ParseException e) {
            logger.error(LogHelper._FUNC_() + e);
        }
        return null;
    }

    /**
     * 根据日期得到当月字符串，格式为yyyyMM
     * @param date
     * @param addMonth
     * @return
     */
    public static String getMonthStringFromDate(Date date, String formatPattern, int addMonth) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatPattern);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, addMonth);
        Date endDate = calendar.getTime();
        return simpleDateFormat.format(endDate);
    }

    /**
     * 根据起止时间，获取对应的月份列表
     * @param startDate YYYYMMDD格式
     * @param endDate YYYYMMDD格式
     * @param sort 排序方式
     * @return
     */
    public static List<String> getYearMonthListByStartAndEndString(String startDate, String endDate, String sort) {
        ArrayList<String> list = new ArrayList<>();
        if (Long.parseLong(startDate) > Long.parseLong(endDate)) {
            return null;
        }
        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FormatPattern.YYYYMM.getFormatPattern());
        try {
            min.setTime(simpleDateFormat.parse(startDate.substring(0, 6)));
            max.setTime(simpleDateFormat.parse(endDate.substring(0, 6)));
            if (AppConstant.SYS_CONSTANT.SORT_ASC.equals(sort)) {
                max.add(Calendar.MONTH, 1);
                Calendar curr = min;
                while (curr.before(max)) {
                    list.add(simpleDateFormat.format(curr.getTime()));
                    curr.add(Calendar.MONTH, 1);
                }
            } else if (AppConstant.SYS_CONSTANT.SORT_DESC.equals(sort)) {
                min.add(Calendar.MONTH, -1);
                Calendar curr = max;
                while (curr.after(min)) {
                    list.add(simpleDateFormat.format(curr.getTime()));
                    curr.add(Calendar.MONTH, -1);
                }
            } else {}
        } catch (ParseException e) {
            logger.error(LogHelper._FUNC_() + e);
        }
        return list;
    }

    public static void main(String[] args) {

        System.out.println(getMonthLastDateStringFromDate(new Date(), MyDateUtil.FormatPattern.YYYYMMDD.getFormatPattern(), 0));
    }
}
