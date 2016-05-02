package com.example.dev.moviedb.service;

import com.example.dev.moviedb.YamdaApplication;
import com.example.dev.moviedb.model.Movie;
import com.example.dev.moviedb.model.MovieAggregator;
import com.example.dev.moviedb.model.async.CallResult;
import com.example.dev.moviedb.model.async.Completion;
import com.example.dev.moviedb.storage.repo.messenger.FetchUpcomingMoviesResultsReceiver;
import com.example.dev.moviedb.utils.NetworkUtils;
import com.example.dev.moviedb.utils.PreferencesUtils;
import com.example.dev.moviedb.utils.Utils;

import android.app.IntentService;
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
 * This subclass of Service serves the purpose of handling asynchronous task requests
 * to obtain data from the api and send it to the repository. It tries to get a list of
 * upcoming movies from the web api.
 *
 * Why it extends from Service and not from IntentService ?
 *
 * Basically because we support the async api given by the retrofit library and we did not wish to
 * change that fact. Being async means the IntentService would terminate without guaranteeing the
 * request would be answer properly. Plus, terminating, means the process looses relevance and so it
 * becomes available to be disposed at any time by the framework if so desires. This kind of behaviour
 * its not what we are looking for. Instead, we roll our Service subclass and we manage the lifecycle
 * ourselves even knowing every method runs in the main thread. This is not a problem since we support
 * the async api from retrofit. We just have to make sure we call "stopSelf" at the end of the callback
 * to signal our work is done.
 *
 * @version 0.0.2
 */
public class FetchUpcomingMoviesService extends Service {

    /*
    to log
     */
    private final String TAG  = Utils.makeLogTag(FetchUpcomingMoviesService.class);



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Starts this service to perform action of fetching a list of upcoming movies from the web api.
     * If the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static Intent prepareIntentToSearch(Context context) {
        return new Intent(context, FetchUpcomingMoviesService.class);
    }


    /**{@inheritDoc}*/
    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        //locate components
        YamdaApplication app = ((YamdaApplication)getApplication());
        Locale lang = app.getCurrentAppLanguage();

        long connType = PreferencesUtils.getConnectionSpecifiedByUser(getResources(), app.getPrefs());
        if (NetworkUtils.isConnected(app, connType)) {
            final int pageNumber = 1;
            Completion<MovieAggregator> queryPages = queryPagesCallback(startId, app);
            app.getApiFetcher().findUpcomingMoviesAsync(lang, queryPages, pageNumber);
        }

        return START_NOT_STICKY;
    }


    /**
     * The handler for when the results are available after querying the api for the
     * upcoming movies.
     *
     * @param startId
     *      The id used to terminate the service once the callback is done executing.
     * @param ctx
     *      The application's context.
     * @return
     *      An instance of Completion to be used as callback when performing async requests.
     */
    private Completion<MovieAggregator> queryPagesCallback(final int startId, final Context ctx){
        return new Completion<MovieAggregator>() {
            @Override
            public void onResult(@NonNull CallResult<MovieAggregator> result) {
                try {
                    List<Movie> list = result.getResult().getmResults();
                    Intent broadcast = new Intent(FetchUpcomingMoviesResultsReceiver.UPCOMING_M_DATA_ACTION);
                    broadcast.putParcelableArrayListExtra(FetchUpcomingMoviesResultsReceiver.UPCOMING_LIST_DATA_KEY,
                            (ArrayList<? extends Parcelable>) list);
                    ctx.sendBroadcast(broadcast);
                    Log.d(TAG, "Broadcast with result sent !");
                    stopSelf(startId);
                } catch (Exception e) {
                    Log.d(TAG, "Problem fetching data. Maybe too much requests..");
                    //if an error happened, we have no interest in letting this live anymore
                    stopSelf(startId);
                }
            }
        };
    }
}
