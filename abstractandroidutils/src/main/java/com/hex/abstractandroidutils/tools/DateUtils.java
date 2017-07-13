package com.hex.abstractandroidutils.tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by alireza on 3/29/17.
 */

public class DateUtils {

    public static Date getDateFromTimeZonable (String timeZoneDate) {
        try {
            TimeZone tz = TimeZone.getTimeZone("Asia/Tehran");
            Calendar cal = Calendar.getInstance(tz);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            sdf.setCalendar(cal);
            cal.setTime(sdf.parse("2013-07-17T03:58:00.000000Z"));
            Date date = cal.getTime();
            return date;
        } catch (Exception e) {
            return null;
        }
    }


}
