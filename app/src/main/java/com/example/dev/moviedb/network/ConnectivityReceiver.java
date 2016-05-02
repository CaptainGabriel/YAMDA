package com.example.dev.moviedb.network;

import com.example.dev.moviedb.YamdaApplication;
import com.example.dev.moviedb.utils.NetworkUtils;
import com.example.dev.moviedb.utils.PreferencesUtils;
import com.example.dev.moviedb.utils.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * In order to know when the device has connected to some network
 * we listen to the connectivity event sent by the system to everyone who
 * subscribe to it. We do this because, if our app starts without being connected to
 * the internet we cannot start any of our services. When we start them we keep a reference to the
 * pending intent used in order to disarm or setup the alarm manager instance later on. Without
 * connectivity we don't start any services to not waste resources therefore there will not be any
 * reference to those intents in our app, ever.
 *
 * @version 0.0.2
 */
public class ConnectivityReceiver extends BroadcastReceiver{

    private final String TAG = Utils.makeLogTag(ConnectivityReceiver.class);


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Internet Connection found.");
        YamdaApplication app = ((YamdaApplication)context.getApplicationContext());
        long connType = PreferencesUtils.getConnectionSpecifiedByUser(app.getResources(), app.getPrefs());
        if (NetworkUtils.isConnected(context, connType)) {
            app.getRepository().forceFetchOfNewData(context);
        }
    }
}
