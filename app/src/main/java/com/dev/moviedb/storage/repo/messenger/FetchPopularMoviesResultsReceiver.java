package com.dev.moviedb.storage.repo.messenger;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.dev.moviedb.YamdaApplication;
import com.dev.moviedb.fragment.options.AbstractSearchOptionsListFragment;
import com.dev.moviedb.storage.repo.Repository;
import com.dev.moviedb.storage.repo.db.DataProvider;
import com.dev.moviedb.utils.ServicesUtils;

import java.util.List;

/**
 * The broadcast receiver that listens for messages sent after results get ready to be handled.
 * It listens for messages that imply the search for a list of the most popular movies is done.
 *
 * @version 0.0.2
 */
public class FetchPopularMoviesResultsReceiver extends BroadcastReceiver {

    /**
     * The action accepted by this receiver.
     */
    public static final String MOST_POPULAR_M_DATA_ACTION = "action.most.popular.movies.list";

    /**
     * The key used by this receiver.
     */
    public static final String MOST_POPULAR_LIST_DATA_KEY = "repo.most.popular.list.data";


    public FetchPopularMoviesResultsReceiver(){}

    @Override
    public void onReceive(Context context, Intent intent) {
        List<Movie> popularList = intent.getParcelableArrayListExtra(MOST_POPULAR_LIST_DATA_KEY);
        YamdaApplication app =((YamdaApplication) context.getApplicationContext());

        //try to insert
        app.getRepository().insertSetOfMovies(Repository.makeUri(DataProvider.POPULAR_TABLE_NAME), popularList);

        //alert for data available
        context.sendBroadcast(new Intent(AbstractSearchOptionsListFragment.DATA_AVAILABLE_ACTION));

        //ask for details when possible
        for (Movie m : popularList) {
            ServicesUtils.askForDetails(app, m);
        }
    }

}
