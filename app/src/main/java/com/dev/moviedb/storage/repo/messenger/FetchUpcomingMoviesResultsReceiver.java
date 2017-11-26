package com.dev.moviedb.storage.repo.messenger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.dev.moviedb.YamdaApplication;
import com.dev.moviedb.fragment.options.AbstractSearchOptionsListFragment;
import com.dev.moviedb.mvvm.model.movies.Movie;
import com.dev.moviedb.storage.repo.Repository;
import com.dev.moviedb.storage.repo.db.DataProvider;
import com.dev.moviedb.utils.ServicesUtils;

import java.util.List;

/**
 * The broadcast receiver that listens for messages sent after results get ready to be handled.
 * It listens for messages that imply the search for a list of upcoming movies is done.
 *
 * @version 1.0.0
 */
public class FetchUpcomingMoviesResultsReceiver extends BroadcastReceiver {

    /**
     * The action accepted by this receiver.
     */
    public static final String UPCOMING_LIST_DATA_KEY = "repo.upcoming.list.data";

    /**
     * The key used by this receiver.
     */
    public static final String UPCOMING_M_DATA_ACTION = "action.upcoming.movies.list";

    public FetchUpcomingMoviesResultsReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //list with instances to save persistently
        List<Movie> upcomingMovies = intent.getParcelableArrayListExtra(UPCOMING_LIST_DATA_KEY);
        YamdaApplication app =((YamdaApplication) context.getApplicationContext());
        //try to insert
        app.getRepository()
                .insertSetOfMovies(Repository.makeUri(DataProvider.UPCOMING_TABLE_NAME), upcomingMovies);

        //alert for data available
        context.sendBroadcast(new Intent(AbstractSearchOptionsListFragment.DATA_AVAILABLE_ACTION));

        //ask for details when possible
        for (Movie m : upcomingMovies) {
            ServicesUtils.askForDetails(app, m);
        }

    }
}
