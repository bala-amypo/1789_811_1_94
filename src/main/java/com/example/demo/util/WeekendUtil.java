package com.example.demo.util;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class WeekendUtil {

    // Check if a date is a weekend
    public static boolean isWeekend(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }

    // Get the next weekend date (Saturday or Sunday) from a given date
    public static LocalDate nextWeekend(LocalDate date) {
        LocalDate d = date;
        while (!isWeekend(d)) {
            d = d.plusDays(1);
        }
        return d;
    }

    // Get the previous weekend date (Saturday or Sunday) from a given date
    public static LocalDate previousWeekend(LocalDate date) {
        LocalDate d = date;
        while (!isWeekend(d)) {
            d = d.minusDays(1);
        }
        return d;
    }
}
