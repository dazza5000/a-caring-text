package com.amicly.acaringtext.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by darrankelinske on 2/14/16.
 */
public class DateUtil {

    public static String getTimeStringFromDate(Date date) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a");
        Calendar calendar = new GregorianCalendar();
        dateFormat.setCalendar(calendar);
        calendar.setTime(date);
        return dateFormat.format(calendar.getTime());

    }

    public static String getDateStringFromDate(Date date) {

        DateFormat dateFormat = DateFormat.getDateInstance();
        Calendar calendar = new GregorianCalendar();
        dateFormat.setCalendar(calendar);
        calendar.setTime(date);
        return dateFormat.format(calendar.getTime());
    }

    public static Date getDateFromDateString(String dateString) {
        DateFormat dateFormat = DateFormat.getDateInstance();
        Date date = null;
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date getTimeFromTimeString(String timeString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a");
        Date time = null;
        try {
            time = dateFormat.parse(timeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static Date mergeDateAndTime(Date date, Date time) {

        GregorianCalendar dateCal = new GregorianCalendar();
        GregorianCalendar timeCal = new GregorianCalendar();

        dateCal.setTime(date);
        timeCal.setTime(time);

        int year = dateCal.get(Calendar.YEAR);
        int month = dateCal.get(Calendar.MONTH);
        int day = dateCal.get(Calendar.DAY_OF_MONTH);

        int hour = timeCal.get(Calendar.HOUR);
        int minute = timeCal.get(Calendar.MINUTE);
        int second = timeCal.get(Calendar.SECOND);

        GregorianCalendar dateTimeCal =
                new GregorianCalendar(year, month, day, hour, minute, second);
        Date dateTime = dateTimeCal.getTime();

        return dateTime;
    }

    public static Long getTimeDifferenceFromNowInMilliseconds(Long scheduledTime){

        Long currentTime = System.currentTimeMillis();
        return scheduledTime - currentTime;
    }
}
