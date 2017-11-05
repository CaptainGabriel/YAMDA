package utils;


import com.dev.moviedb.utils.DateUtils;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.junit.Test;

/**
 *
 */
public class DateUtilsTest {

    /*
    JodaTimeAndroid.init(MockContext); cannot be done using JUnit, so
    we use Joda-Time (normal version) to test. Is the same but not optimized for android.
     */

    @Test
    public void getYearFromValidDate(){
        final String validDate = "2005-11-22";
        final String expectedYear = "2005";
        Assert.assertEquals(expectedYear, DateUtils.getYear(validDate));
    }

    @Test
    public void getMonthAndDayFromValidDate(){
        final String validDate = "2005-11-22";
        final String expectedPair = "11/22";
        final String pair = DateUtils.getMonthAndDay(validDate);
        Assert.assertEquals(expectedPair, pair);
    }

    @Test
    public void getYearFromInvalidDate(){
        final String validDate = "2005";
        final String expectedYear = "TBA";
        Assert.assertEquals(expectedYear, DateUtils.getYear(validDate));
    }

    @Test
    public void getYearFromNullString(){
        final String validDate = null;
        final String expectedYear = "TBA";
        Assert.assertEquals(expectedYear, DateUtils.getYear(validDate));
    }

    @Test
    public void getYearFromEmptyString(){
        final String validDate = "";
        final String expectedYear = "TBA";
        Assert.assertEquals(expectedYear, DateUtils.getYear(validDate));
    }


    @Test
    public void getMonthFromValidDate(){
        final String validDate = "2005-11-22";
        final String expectedMonth = "11";
        Assert.assertEquals(expectedMonth, DateUtils.getMonth(validDate));
    }

    @Test
    public void getDayFromValidDate(){
        final String validDate = "2005-11-02";
        final String expectedDay = "02";
        Assert.assertEquals(expectedDay, DateUtils.getDay(validDate));
    }


    @Test
    public void buildInvalidDateFromInvalidData(){
        final String invalidDate = "invalid date goes here, obviously";
        org.junit.Assert.assertNull(DateUtils.buildDate(invalidDate));
    }

    @Test
    public void buildValidDateFromValidData(){
        final String validDate = "2005-11-02";
        DateTime result = DateUtils.buildDate(validDate);

        int year = Integer.valueOf(DateUtils.getYear(validDate));
        int m = Integer.valueOf(DateUtils.getMonth(validDate));
        int d = Integer.valueOf(DateUtils.getDay(validDate));
        DateTime expectedDate = new DateTime(year, m, d, 0, 0);
        org.junit.Assert.assertTrue(result.compareTo(expectedDate.toInstant()) == 0);
    }

    @Test
    public void testIfTodayIsNotToday(){
        //update date
        final String validDate = "2015-12-01";
        Assert.assertFalse(DateUtils.isToday(validDate));
    }

}
