package com.dev.moviedb.utils;

import org.joda.time.DateTime;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class that handles data formats and operations.
 * It makes use of the JodaTime library.
 *
 * @version 0.0.2
 */
public class DateUtils {

    private DateUtils(){}

    public static final String TO_BE_ANNOUNCED = "TBA";

    /**
     * Helper method that based upon a string that represents
     * a valid date in the format "yyyy-mm-dd" returns the year or
     * the acronym "TBA" if the parameter is invalid.
     *
     * "Low level" implementation because we cannot call buildDate or will overflow the call stack.
     *
     * @param dateStr valid date in the format "yyyy-mm-dd"
     * @return the year
     */
    public static String getYear(String dateStr){
        return (null == dateStr || dateStr.isEmpty() || !hasValidFormat(dateStr)) ? TO_BE_ANNOUNCED :
                dateStr.substring(0, dateStr.indexOf("-"));
    }

    public static String getMonth(String date){
        return (null == date || date.isEmpty() || !hasValidFormat(date)) ? "0" : date.substring(5, 7);
    }


    public static String getDay(String date){
        return (null == date || date.isEmpty() || !hasValidFormat(date)) ? "0" : date.substring(8);
    }

    /**
     * Helper method that based upon a string that represents
     * a valid date in the format "yyyy-mm-dd" returns the month/day or
     * the acronym "TBA" if the parameter is invalid.
     *
     *
     */
    public static String getMonthAndDay(String dateStr){
        return (null == dateStr || dateStr.isEmpty() || !hasValidFormat(dateStr)) ? TO_BE_ANNOUNCED :
                getMonth(dateStr) + "/" + getDay(dateStr);
    }

    /**
     * Checks if the given date follows the pattern "yyyy-mm-dd".
     */
    private static boolean hasValidFormat(String dateStr) {
        Matcher matcher= Pattern.compile("\\d{4}\\-\\d{2}\\-\\d{2}").matcher(dateStr);
        return matcher.matches();
    }

    /**
     * Builds an instance of DateTime from the given string if one follows the
     * pattern "yyyy-mm-dd". Otherwise return null;
     *
     */
    public static DateTime buildDate(String rawDate){
        try {
            int year = Integer.valueOf(DateUtils.getYear(rawDate));
            int m = Integer.valueOf(DateUtils.getMonth(rawDate));
            int d = Integer.valueOf(DateUtils.getDay(rawDate));
            return new DateTime(year, m, d, 0, 0);
        }catch(NumberFormatException ex){
            return null;
        }
    }

    /**
     * Checks if a given date is of today's day.
     * @return True if date is equal to today's date, false otherwise.
     */
    public static boolean isToday(String date) {
        DateTime release = buildDate(date);
        if(release == null)
            return false;
        return cmpDate(release, DateTime.now());
    }

    /**
     * Checks if a given date is of tomorrow's day.
     * @return True if date is equal to tomorrow's date, false otherwise.
     */
    public static boolean isTomorrow(String date) {
        DateTime release = buildDate(date);
        if(release == null)
            return false;
        return cmpDate(release, DateTime.now().plusDays(1));
    }

    /**
     * Given two dates, compare both.
     * @return True if date is equal, false otherwise.
     */
    private static boolean cmpDate(DateTime dt1, DateTime dt2){
        return dt1.getYear() == dt2.getYear() &&
                dt1.getMonthOfYear() == dt2.getMonthOfYear() &&
                dt1.getDayOfMonth() == dt2.getDayOfMonth();
    }


}
