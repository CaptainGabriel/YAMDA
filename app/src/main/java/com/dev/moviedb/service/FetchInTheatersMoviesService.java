package com.dev.moviedb.service;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.dev.moviedb.YamdaApplication;
import com.dev.moviedb.model.MovieAggregator;
import com.dev.moviedb.model.async.CallResult;
import com.dev.moviedb.model.async.Completion;
import com.dev.moviedb.mvvm.data.source.remote.MovieApiProvider;
import com.dev.moviedb.storage.repo.messenger.FetchInTheatersMoviesResultsReceiver;
import com.dev.moviedb.utils.NetworkUtils;
import com.dev.moviedb.utils.PreferencesUtils;
import com.dev.moviedb.utils.Utils;

import java.util.ArrayList;
import java.util.Locale;


/**
 * This subclass of Service serves the purpose of handling asynchronous task requests
 * to obtain data from the api and send it to the repository. It tries to get a list of
 * movies that are currently playing in theaters.
 *
 * Why it extends from Service and not from IntentService ?
 *
 * Basically because we support the async api given by the retrofit library and we did not wish to
 * change that fact. Being async means the IntentService would terminate without guaranteeing the
 * request would be answered properly. Plus, terminating, means the process looses relevance and so it
 * becomes available to be disposed at any time by the framework if so desires. This kind of behaviour
 * its not what we are looking for. Instead, we rolled our Service subclass and we manage the lifecycle
 * ourselves even knowing every method runs in the main thread. This is not a problem since we support
 * the async api from retrofit. We just have to make sure we call "stopSelf" at the end of the callback
 * to signal that our work is done.
 *
 *
 * @version 0.0.2
 */
public class FetchInTheatersMoviesService extends Service {


    /*
    to log
     */
    private final String TAG  = Utils.makeLogTag(FetchInTheatersMoviesService.class);

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Starts this service to perform the action  of fetching a list of movies that are currently
     * playing in theaters. If the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static Intent prepareIntentToSearch(Context context) {
        return new Intent(context, FetchInTheatersMoviesService.class);
    }

    /**{@inheritDoc}*/
    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {

        YamdaApplication app = ((YamdaApplication)getApplication());
        Locale lang = app.getCurrentAppLanguage();
        MovieApiProvider mMovieApiServiceFetcher = app.getApiFetcher();

        //perform tasks if the device is connected and respects the user's settings
        long connType = PreferencesUtils.getConnectionSpecifiedByUser(getResources(), app.getPrefs());
        if (NetworkUtils.isConnected(app, connType)) {
            mMovieApiServiceFetcher.findInTheatersMoviesAsync(lang, handleResultsCallback(startId));
        }else stopSelf(startId);

        return START_NOT_STICKY;
    }

    private Completion<MovieAggregator> handleResultsCallback(final int startId) {
        return new Completion<MovieAggregator>() {
            @Override
            public void onResult(@NonNull CallResult<MovieAggregator> result) {
                try {
                    Intent broadcast = new Intent(FetchInTheatersMoviesResultsReceiver.IN_THEATERS_M_DATA_ACTION);
                    broadcast.putParcelableArrayListExtra(FetchInTheatersMoviesResultsReceiver.IN_THEATERS_LIST_DATA_KEY,
                            (ArrayList<? extends Parcelable>) result.getResult().getmResults());
                    sendBroadcast(broadcast);

                    Log.d(TAG, "Broadcast with result sent !");
                    stopSelf(startId);
                } catch (Exception e) {
                    Log.d(TAG, "Problem fetching data. Maybe too much requests..");
                    stopSelf(startId);
                }
            }
        };
    }


}
