package net.codigo.bookingsystem.base.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static String getNowString() {
        return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
    }

    public static String getNowStringMinuteHour(int hour) {
        Date currentDate = new Date();
        long modifiedTime = currentDate.getTime() - (4 * 60 * 60 * 1000);
        Date modifiedDate = new Date(modifiedTime);
        return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(modifiedDate);
    }

    public static long getNowDate() {
        String nowDate = getNowString();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long date = 0;
        try {
            date =simpleDateFormat.parse(nowDate).getTime()/1000;
        }catch (Exception e){
            e.printStackTrace();
        }
        return date;
    }
    public static long getNowDateMinuteHour(int hour) {
        String nowDate = getNowStringMinuteHour(hour);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long date = 0;
        try {
            date =simpleDateFormat.parse(nowDate).getTime()/1000;
        }catch (Exception e){
            e.printStackTrace();
        }
        return date;
    }
    public static String longToStringDate(long date) {
        return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date(date*1000));
    }

    public static long stringToLongDate(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long dateLong = 0;
        try {
            dateLong =simpleDateFormat.parse(date).getTime()/1000;
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(dateLong);
        return dateLong;
    }

}
