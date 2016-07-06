
package com.zhansy.ordermanage.utils;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeUtil {
//    public static String converTime(long timestamp) {
//        long currentSeconds = System.currentTimeMillis() / 1000;
//        long timeGap = currentSeconds - timestamp;// 与现在时间相差秒数
//        String timeStr = null;
//        if (timeGap <= 24 * 60 * 60) {// 今天
//            timeStr = DateUtils.formatDate(new Date(timestamp * 1000), "HH:mm");
//        } else if (timeGap <= 24 * 60 * 60 * 365) { // 今年
//            timeStr = DateUtils.formatDate(new Date(timestamp * 1000),
//                    "MM-dd HH:mm");
//        } else {
//            timeStr = DateUtils.formatDate(new Date(timestamp * 1000),
//                    DateUtils.DEFAULT_PATTERN);
//        }
//        return timeStr;
//    }

    /**
     * 是否同一天
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDate(long date1, long date2) {
        long days1 = date1 / (1000 * 60 * 60 * 24);
        long days2 = date2 / (1000 * 60 * 60 * 24);
        return days1 == days2;
    }

    public static String getStandardTime(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 HH:mm");
        Date date = new Date(timestamp * 1000);
        sdf.format(date);
        return sdf.format(date);
    }

    public static String strToDateMotnOfDay(long strDate) {
        long currentSeconds = System.currentTimeMillis();
        long timeGap = currentSeconds - strDate;// 与现在时间相差秒数
        if (isSameDate(strDate, currentSeconds)) {// 今天
            return "今天";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd");
        Date date = new Date(strDate * 1000);
        sdf.format(date);
        return sdf.format(date);
    }

    public static String getYYYYmmDD(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date(timestamp);
        sdf.format(date);
        return sdf.format(date);
    }

    /**
     * 获取当前完整的时间
     *
     * @param timestamp
     * @return
     */
    public static String getStandardFullTime(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(timestamp);
        sdf.format(date);
        return sdf.format(date);
    }

    /**
     * 获取指定格式的时间
     *
     * @param timestamp
     * @return
     */
    public static String getFormatTime(long timestamp, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = new Date(timestamp);
        sdf.format(date);
        return sdf.format(date);
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getTimeNow(String format) {
        SimpleDateFormat simpleDateTimeFormat = new SimpleDateFormat(format);
        Calendar c = Calendar.getInstance(Locale.CHINESE);
        return simpleDateTimeFormat.format(c.getTime());
    }

    public static Long getCurentTime() {
        Date date = new Date();
        return date.getTime() / 1000;
    }

//    public static String getDate(Context context, long date, String text) {
//        long days1 = date / (1000 * 60 * 60 * 24);
//        long days2 = System.currentTimeMillis() / (1000 * 60 * 60 * 24);
//        long timeGap = days2 - days1;
//        if (timeGap == 0)
//            return context.getString(R.string.today);
//        else if (timeGap == 1)
//            return context.getString(R.string.yestoday);
//        else if (timeGap == 2)
//            return context.getString(R.string.before_yesterday);
//        else
//            return text;
//    }
//
//    public static CharSequence getRelativeTime(final Date date) {
//        Context context = DiskApplication.getInstance().getApplicationContext();
//        long now = System.currentTimeMillis();
//        long dif = Math.abs(now - date.getTime()) / 1000;// 秒
//        if (dif < 1) {// 刚刚
//            return context.getString(R.string.time_moment_ago);
//        }
//        if (dif < 60) {// 1分钟内，x秒前
//            return context.getString(R.string.time_second_ago, dif);
//        }
//        if (dif < 60 * 60) {// 1小时内，x分钟前
//            return context.getString(R.string.time_minute_ago, dif / 60);
//        }
//        if (dif < 60 * 60 * 24) {// 24小时内，x小时前
//            return context.getString(R.string.time_hour_ago, dif / 60 / 60);
//        }
//        // android.text.format.DateUtils依赖与Locale，有些手机会显示英文
////        if (dif < 60 * 60 * 24 * 1000)
////            return android.text.format.DateUtils.getRelativeTimeSpanString(date.getTime(), now,
////                    SECOND_IN_MILLIS, FORMAT_SHOW_DATE | FORMAT_SHOW_YEAR
////                            | FORMAT_NUMERIC_DATE);
////        else {
////            return DateUtils.formatDate(date, DateUtils.HOUR_PATTERN);
////        }
//        return DateUtils.formatDate(date, DateUtils.HOUR_PATTERN);
//    }

}
