package com.example.ricardopeixe.moviedb.service;

import com.example.ricardopeixe.moviedb.YamdaApplication;
import com.example.ricardopeixe.moviedb.model.Movie;
import com.example.ricardopeixe.moviedb.model.MovieAggregator;
import com.example.ricardopeixe.moviedb.model.async.CallResult;
import com.example.ricardopeixe.moviedb.model.async.Completion;
import com.example.ricardopeixe.moviedb.storage.repo.messenger.FetchPopularMoviesResultsReceiver;
import com.example.ricardopeixe.moviedb.utils.NetworkUtils;
import com.example.ricardopeixe.moviedb.utils.PreferencesUtils;
import com.example.ricardopeixe.moviedb.utils.Utils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

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
    private final String TAG = Utils.makeLogTag(FetchPopularMoviesService.class);

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
