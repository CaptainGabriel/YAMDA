package com.example.ricardopeixe.moviedb.service;

import com.example.ricardopeixe.moviedb.R;
import com.example.ricardopeixe.moviedb.YamdaApplication;
import com.example.ricardopeixe.moviedb.model.async.CallResult;
import com.example.ricardopeixe.moviedb.model.async.Completion;
import com.example.ricardopeixe.moviedb.storage.repo.db.DataRecord;
import com.example.ricardopeixe.moviedb.utils.DateUtils;
import com.example.ricardopeixe.moviedb.utils.NetworkUtils;
import com.example.ricardopeixe.moviedb.utils.NotificationUtils;
import com.example.ricardopeixe.moviedb.utils.PreferencesUtils;
import com.example.ricardopeixe.moviedb.utils.Utils;

import android.app.Application;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

/**
 * This service checks the upcoming movies list for movies about to release
 * and sends an user notification if so.
 *
 * @version 1.0.0
 */
public class NotificationReleaseService extends Service {

    private final String TAG = Utils.makeLogTag(NotificationReleaseService.class);

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "OnStartCommand");
        YamdaApplication app = ((YamdaApplication) getApplication());

        long connType = PreferencesUtils.getConnectionSpecifiedByUser(getResources(), app.getPrefs());
        if (NetworkUtils.isConnected(app, connType)) {
            app.getRepository().getFavoriteRecords(new Completion<List<DataRecord>>() {
                @Override
                public void onResult(@NonNull CallResult<List<DataRecord>> result) {
                    try {
                        for (DataRecord m : result.getResult()) {
                            if (DateUtils.isToday(m.getReleaseDate()) || DateUtils.isTomorrow(m.getReleaseDate())) {
                                NotificationUtils.simpleNotification(getApplication(),
                                        R.drawable.ic_local_movies_white_36dp,
                                        m.getOriginalTitle(),
                                        getApplicationContext().getResources().getString(R.string.notification_content));
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }else stopSelf(startId);

        return START_NOT_STICKY;
    }


    public static Intent prepareIntentToSearch(Application application) {
        return new Intent(application, NotificationReleaseService.class);
    }
}
