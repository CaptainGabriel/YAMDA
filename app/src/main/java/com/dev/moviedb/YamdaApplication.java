package com.dev.moviedb;

import android.app.AlarmManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.util.Log;

import com.dev.moviedb.fragment.options.AbstractSearchOptionsListFragment;
import com.dev.moviedb.storage.repo.IRepository;
import com.dev.moviedb.storage.repo.Repository;
import com.dev.moviedb.storage.repo.db.DatabaseHelper;
import com.dev.moviedb.utils.NetworkUtils;
import com.dev.moviedb.utils.PreferencesUtils;
import com.dev.moviedb.utils.ToastUtils;
import com.dev.moviedb.utils.Utils;

import net.danlew.android.joda.JodaTimeAndroid;

import java.util.Locale;

import petegabriel.com.yamda.R;

/**
 *
 *
 * @version 1.0.0
 */
public class YamdaApplication extends Application{

    /**
     * For debug purposes.
     */
    private final String TAG = Utils.makeLogTag(YamdaApplication.class);

    /**
     * This reference holds the most updated language configuration.
     * It gets updated every time the onConfigurationChanged callback
     * is called by the framework.
     */
    private Locale mCurrentAppLanguage;

    /**
     * The instance that knows how to obtain and/or store data inside our application
     * in order to support offline mode.
     */
    private IRepository mRepoOfData;


    /**
     * Reference to the SharedPreferences created in {@link com.dev.moviedb.activity.UserPreferencesActivity}.
     */
    private SharedPreferences prefs;

    private AlarmManager alarmManager;

    private DatabaseHelper mDatabaseHelper;

    /** {@inheritDoc} */
    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);

        mCurrentAppLanguage = getResources().getConfiguration().locale;

        mRepoOfData = new Repository(this);

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        mDatabaseHelper = new DatabaseHelper(getApplicationContext());

        //read preferences in order to get the update interval setup by the user
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        long defaultInterval = 1;//PreferencesUtils.getUpdateInterval(prefs);
        Log.d(TAG, "Default Time Interval: " + defaultInterval);
        getRepository().scheduleServices(this, defaultInterval);
    }

    public DatabaseHelper getDatabaseHelper() {
        return mDatabaseHelper;
    }

    public AlarmManager getAlarmManager() {
        return alarmManager;
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
            Check what user has to say about new language configuration.
             */
            long connType = PreferencesUtils.getConnectionSpecifiedByUser(getResources(), prefs);
            boolean toDeleteObsoleteData =  PreferencesUtils.isObsoleteDataToBeDeleted(prefs);
            if (toDeleteObsoleteData) {
                Log.d(TAG, "Erase obsolete data and try to retrieve most updated data.");
                //clean data
                getRepository().clearData();
                //let them know you care about their "state"
                Intent erasedDataMessage = new Intent(AbstractSearchOptionsListFragment.DATA_ERASED_ACTION);
                this.sendBroadcast(erasedDataMessage);

                if (NetworkUtils.isConnected(this, connType)) {
                    Log.d(TAG, "Force fetch of new data");
                    getRepository().forceFetchOfNewData(this);
                } else {
                    String msg = this.getResources().getString(R.string.fetching_info_from_server);
                    ToastUtils.showShortMessage(msg, this);
                }
            }
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

    /**
     * Gives back the running instance of the repository.
     */
    public synchronized IRepository getRepository() {
        return mRepoOfData;
    }


    /**
     * Returns a reference to the default shared preferences.
     */
    public synchronized SharedPreferences getPrefs() {
        return prefs;
    }
}
