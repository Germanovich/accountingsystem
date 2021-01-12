package com.hermanovich.accountingsystem.util;

import lombok.experimental.UtilityClass;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@UtilityClass
public final class DataManagerSystem {

    private final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy (HH:mm:ss:SSS)");

    public static Date parseDate(String date) {
        try {
            return format.parse(date);
        } catch (Exception e) {
            return null;
        }
    }

    public Date getDateDaysAgo(Integer numOfDaysAgo) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -1 * numOfDaysAgo);
        return calendar.getTime();
    }

    public Date addDays(Date date, Integer days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    public Integer getNumberOfDays(Date firstDate, Date secondDate) {
        Calendar firstCalendar = Calendar.getInstance();
        Calendar secondCalendar = Calendar.getInstance();
        firstCalendar.setTime(firstDate);
        secondCalendar.setTime(secondDate);
        return secondCalendar.get(Calendar.DAY_OF_YEAR) - firstCalendar.get(Calendar.DAY_OF_YEAR);
    }
}
