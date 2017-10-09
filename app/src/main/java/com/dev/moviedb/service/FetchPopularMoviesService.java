package com.dev.moviedb.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dev.moviedb.YamdaApplication;
import com.dev.moviedb.model.Movie;
import com.dev.moviedb.model.MovieAggregator;
import com.dev.moviedb.model.async.CallResult;
import com.dev.moviedb.model.async.Completion;
import com.dev.moviedb.storage.repo.messenger.FetchPopularMoviesResultsReceiver;
import com.dev.moviedb.utils.NetworkUtils;
import com.dev.moviedb.utils.PreferencesUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * This class perform the fetching of a list of movies
 * that are currently the most popular in the web api database.
 *
 * @version 0.0.2
 */
public class FetchPopularMoviesService extends Service {

    /*
    to log
     */

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * It creates an intent with the given movie as parameter in order to be possible
     * to obtain it when the service executes.
     *
     * @return an intent to this service
     */
    public static Intent prepareIntentToSearch(Context context) {
        return new Intent(context, FetchPopularMoviesService.class);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        //locate components
        YamdaApplication app = ((YamdaApplication) getApplication());
        Locale lang = app.getCurrentAppLanguage();

        long connType = PreferencesUtils.getConnectionSpecifiedByUser(getResources(), app.getPrefs());
        if (NetworkUtils.isConnected(app, connType)) {
            app.getApiFetcher().findMostPopularMoviesAsync(lang, handleResultsCallback(startId));
        } else {
            stopSelf(startId);
        }

        return START_NOT_STICKY;
    }

    private Completion<MovieAggregator> handleResultsCallback(final int startId) {
        return new Completion<MovieAggregator>() {
            @Override
            public void onResult(@NonNull CallResult<MovieAggregator> result) {
                try {
                    List<Movie> popularList = result.getResult().getmResults();

                    Intent broadcast = new Intent(FetchPopularMoviesResultsReceiver.MOST_POPULAR_M_DATA_ACTION);
                    broadcast.putParcelableArrayListExtra(FetchPopularMoviesResultsReceiver.MOST_POPULAR_LIST_DATA_KEY,
                            (ArrayList<? extends Parcelable>) popularList);
                    sendBroadcast(broadcast);

                    //TODO Log.d(TAG, "Broadcast with result sent !");
                    stopSelf(startId);
                } catch (Exception e) {
                    //TODO Log.d(TAG, "Problem fetching data. Maybe too much requests..");
                    stopSelf(startId);
                }
            }
        };
    }
}
