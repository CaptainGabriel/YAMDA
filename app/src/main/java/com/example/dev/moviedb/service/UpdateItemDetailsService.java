package com.example.dev.moviedb.service;

import com.example.dev.moviedb.YamdaApplication;
import com.example.dev.moviedb.model.Movie;
import com.example.dev.moviedb.model.async.CallResult;
import com.example.dev.moviedb.model.async.Completion;
import com.example.dev.moviedb.storage.repo.messenger.UpdateItemMessageReceiver;
import com.example.dev.moviedb.utils.NetworkUtils;
import com.example.dev.moviedb.utils.PreferencesUtils;
import com.example.dev.moviedb.utils.Utils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Locale;

/**
 * This service performs the request for an item based upon a given id.
 * It sens the updated/new information as a broadcast.
 *
 * @version 1.0.0
 */
public class UpdateItemDetailsService extends Service {
    /*
    to log
     */
    private final String TAG = Utils.makeLogTag(UpdateItemDetailsService.class);

    /**
     * The key used to save data in the intent returned by the method prepareIntentToSearch.
     */
    public static final String ITEM_TO_UPDATE = "updatedetailsservice.item.saved.in.intent";

    /**
     * Get an intent from this Service ready to be used to start it.
     */
    public static Intent prepareIntentToSearch(Context context, Movie movie) {
        return new Intent(context, UpdateItemDetailsService.class)
                .putExtra(UpdateItemDetailsService.ITEM_TO_UPDATE, movie);
    }

    /** {@inheritDoc} */
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        Log.d(TAG, "#OnStartCommand");
        //locate components
        final YamdaApplication app = ((YamdaApplication) getApplication());
        Locale lang = app.getCurrentAppLanguage();
        final Movie item = intent.getParcelableExtra(ITEM_TO_UPDATE);

        long connType = PreferencesUtils.getConnectionSpecifiedByUser(getResources(), app.getPrefs());
        if (NetworkUtils.isConnected(app, connType)) {
            Log.d(TAG, "Connected. Fetching data.");
            String appendToResult = "trailers";
            app.getApiFetcher()
                    .findMovieByIdAsync(item.getPrimaryFacts().getId(), lang, appendToResult, handleMovie(startId));
        } else {
            Log.d(TAG, "Not Connected. Cant fetch data.");
            stopSelf(startId);
        }

        return START_NOT_STICKY;
    }

    /**
     * The callback invoked when the results are available.
     *
     * @param startId The id to terminate the service.
     * @return an instance of Completion
     */
    private Completion<Movie> handleMovie(final int startId) {
        return new Completion<Movie>() {
            @Override
            public void onResult(@NonNull final CallResult<Movie> resultFromWeb) {
                try {
                    Movie fromWeb = resultFromWeb.getResult();
                    Log.d(TAG, "Found details for the movie \"" + fromWeb.getPrimaryFacts().getTitle()
                            + "\" with id #" + fromWeb.getPrimaryFacts().getId());

                    Intent broadcast = new Intent(UpdateItemMessageReceiver.NEW_ITEM_RECEIVED_ACTION);
                    broadcast.putExtra(UpdateItemMessageReceiver.NEW_ITEM_RECEIVED_KEY, fromWeb);
                    sendBroadcast(broadcast);
                    Log.d("STOP-ID", startId + "");
                    stopSelf(startId);
                } catch (Exception e) {
                    //delay is not enough
                    Log.e(TAG, "Delay is not enough, too many api requests.");
                    stopSelf(startId);
                }
            }
        };
    }


}
