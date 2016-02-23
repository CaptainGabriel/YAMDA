package com.example.ricardopeixe.moviedb.storage.repo.messenger;

import com.example.ricardopeixe.moviedb.YamdaApplication;
import com.example.ricardopeixe.moviedb.storage.repo.Repository;
import com.example.ricardopeixe.moviedb.utils.PreferencesUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Receiver that listens for boot events.
 * The alarm schedule for alarm manager is wiped clean on a reboot.
 * Hence, we need to get control at boot time to re-establish the alarms,
 * if we want them to start up again after a reboot.
 *
 * @version 1.0.0
 */
public class OnBootReceiver extends BroadcastReceiver {

    public OnBootReceiver(){}

    @Override
    public void onReceive(Context context, Intent intent) {
        YamdaApplication app = ((YamdaApplication)context.getApplicationContext());
        Repository repo = (Repository) app.getRepository();
        long interval = PreferencesUtils.getUpdateInterval(app.getPrefs());
        repo.scheduleServices(app, interval);
    }
}
