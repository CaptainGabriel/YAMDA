package com.example.ricardopeixe.moviedb.storage.repo.messenger;

import com.example.ricardopeixe.moviedb.YamdaApplication;
import com.example.ricardopeixe.moviedb.utils.Utils;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * When changes related to the amount of time between verification of new data in the web, a message
 * should be sent and this class is responsible to catch that message.
 * Accordingly with the message, the services that search for data are rescheduled.
 *
 * @version 1.0.0
 */
public class OnPreferencesChangeReceiver extends BroadcastReceiver {

    private final String TAG  = Utils.makeLogTag(UpdateItemMessageReceiver.class);

    /**
     * The action expected
     */
    public static final String ON_PREFERENCES_CHANGE_NOTIFICATION = "on.preferences.change.message.received";

    /**
     * Key to used when passing parameters through the intent.
     */
    public static final String ON_PREFERENCES_CHANGE_NOTIFICATION_KEY = "on.preferences.change.message.received.key";


    public OnPreferencesChangeReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        YamdaApplication app = ((YamdaApplication) context.getApplicationContext());
        long newTimeInterval = intent.getLongExtra(ON_PREFERENCES_CHANGE_NOTIFICATION_KEY, AlarmManager.INTERVAL_DAY);
        Log.d(TAG, "Rescheduling services with new time interval: " + newTimeInterval);
        app.getRepository().rescheduleServices(app.getAlarmManager(), newTimeInterval);
    }
}
