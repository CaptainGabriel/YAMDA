package com.example.dev.moviedb.utils;

import com.example.dev.moviedb.R;
import com.example.dev.moviedb.activity.UserPreferencesActivity;

import android.app.AlarmManager;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.util.Log;

/**
 * Provides easy retrieval of preferences.
 *
 * @version 0.0.2
 */
public class PreferencesUtils {

    /**
     * The amount of milliseconds that exist inside one minute.
     */
    private static final int ONE_MINUTE_IN_MILI = 60000;

    private PreferencesUtils(){/*no instances*/}


    /**
     * Read the value present at the preferences related to the time interval used to schedule
     * data updates. The default value is 24 hours.
     *
     */
    public static long getUpdateInterval(SharedPreferences prefs){
        long defaultInterval = readTimeInterval(prefs);
        Log.d("UpdateInterval", defaultInterval + " minutos");
        return (defaultInterval != UserPreferencesActivity.INVALID_TIME_INTERVAL) ?
                (defaultInterval * ONE_MINUTE_IN_MILI) : AlarmManager.INTERVAL_DAY;
    }

    /**
     * Read the time interval specified in the preferences file by the user.
     *
     * @param prefs
     *      The default SharedPreferences instance.
     * @return
     *      Tthe value read from the preferences or INVALID_TIME_INTERVAL (-1).
     *      The later should be interpreted as an error.
     */
    private static long readTimeInterval(SharedPreferences prefs){
        try {
            String tmp = prefs.getString(UserPreferencesActivity.DATA_UPDATES_INTERVAL_KEY, "");
            return Integer.valueOf(tmp);
        }catch(NumberFormatException ex){
            //xml specifies that only numbers are allowed but we love defensive code so..
            Log.d("ReadInterval", "Invalid time interval update - Only numbers expected");
            return UserPreferencesActivity.INVALID_TIME_INTERVAL;
        }
    }


    public static boolean readObsoleteDataDecision(SharedPreferences prefs){
        return prefs.getBoolean(UserPreferencesActivity.OBSOLETE_DATA_DECISION_KEY, false);
    }

    /**
     * It tries to find the type of connection defined by the user in preferences.
     * It returns -1 if any type can be used.
     * If the WiFi was the value specified it will return the constant ConnectivityManager.TYPE_WIFI (1).
     * If the mobile data type was selected it will return ConnectivityManager.TYPE_MOBILE (0).
     */
    public static long getConnectionSpecifiedByUser(Resources res, SharedPreferences prefs){
        //possible values
        final String WIFI = res.getString(R.string.wifi_conn);
        final String MOBILE = res.getString(R.string.mobile_conn);

        String valueFromPrefs = prefs.getString(UserPreferencesActivity.TYPE_OF_CONNECTION_KEY, "");

        if(valueFromPrefs.equals(WIFI))
            return ConnectivityManager.TYPE_WIFI;
        if(valueFromPrefs.equals(MOBILE))
            return ConnectivityManager.TYPE_MOBILE;
        else return -1;
    }


    /**
     * Checks if the user wants data to be deleted if language configuration changes.
     *
     * @param prefs
     *      Default SharedPreferences instance.
     * @return
     *      True if the user wants data to be deleted, false otherwise.
     */
    public static boolean isObsoleteDataToBeDeleted(SharedPreferences prefs){
        return prefs.getBoolean(UserPreferencesActivity.OBSOLETE_DATA_DECISION_KEY, false);
    }

}
