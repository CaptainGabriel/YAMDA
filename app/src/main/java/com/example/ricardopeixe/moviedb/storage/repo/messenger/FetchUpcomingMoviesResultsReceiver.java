package com.example.ricardopeixe.moviedb.storage.repo.messenger;

import com.example.ricardopeixe.moviedb.YamdaApplication;
import com.example.ricardopeixe.moviedb.fragment.options.AbstractSearchOptionsListFragment;
import com.example.ricardopeixe.moviedb.model.Movie;
import com.example.ricardopeixe.moviedb.storage.repo.Repository;
import com.example.ricardopeixe.moviedb.storage.repo.db.DataProvider;
import com.example.ricardopeixe.moviedb.utils.ServicesUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

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
