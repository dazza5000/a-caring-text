package com.amicly.acaringtext.util;

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

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM-dd-yy");
        Calendar calendar = new GregorianCalendar();
        dateFormat.setCalendar(calendar);
        calendar.setTime(date);
        return dateFormat.format(calendar.getTime());

    }
}
