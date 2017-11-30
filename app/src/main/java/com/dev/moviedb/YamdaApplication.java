package com.dev.moviedb;

import android.app.AlarmManager;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.util.Log;

import com.squareup.picasso.Picasso;

import net.danlew.android.joda.JodaTimeAndroid;

import java.util.Locale;

/**
 *
 *
 * @version 1.0.0
 */
public class YamdaApplication extends Application{

    /**
     * For debug purposes.
     */
    private final String TAG = "";

    /**
     * This reference holds the most updated language configuration.
     * It gets updated every time the onConfigurationChanged callback
     * is called by the framework.
     */
    private Locale mCurrentAppLanguage;


    private SharedPreferences prefs;

    private AlarmManager alarmManager;

    /** {@inheritDoc} */
    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);

        Picasso.with(this)
                .setIndicatorsEnabled(petegabriel.com.yamda.BuildConfig.SHOW_PICASSO_INDICATORS);


        mCurrentAppLanguage = getResources().getConfiguration().locale;


        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        //read preferences in order to get the update interval setup by the user
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        long defaultInterval = 1;//PreferencesUtils.getUpdateInterval(prefs);
        Log.d(TAG, "Default Time Interval: " + defaultInterval);

    }

    /** {@inheritDoc} */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        //only interested in this change. Rotation screen changes should not execute this code.
        if (!getCurrentAppLanguage().equals(newConfig.locale)) {
            mCurrentAppLanguage = newConfig.locale;
            Log.d(TAG, "Language Changed - New Language: " + mCurrentAppLanguage.getDisplayLanguage());
            /*
            TODO Check what user has to say about new language configuration erasure.
             */

        }
    }


    /**
     * Getter of the most updated language configuration.
     *
     * @return the current supported language
     */
    public synchronized Locale getCurrentAppLanguage() {
        return mCurrentAppLanguage;
    }

}
