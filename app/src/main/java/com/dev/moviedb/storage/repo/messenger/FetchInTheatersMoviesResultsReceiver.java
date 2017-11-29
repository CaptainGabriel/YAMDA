package com.dev.moviedb.storage.repo.messenger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.dev.moviedb.YamdaApplication;
import com.dev.moviedb.fragment.options.AbstractSearchOptionsListFragment;
import com.dev.moviedb.storage.repo.Repository;
import com.dev.moviedb.storage.repo.db.DataProvider;
import com.dev.moviedb.utils.ServicesUtils;

import java.util.List;

/**
 * The broadcast receiver that listens for messages sent after results get ready to be handled.
 * It listens for messages that imply the search for a list of movies currently in theaters is done.
 *
 * @version 0.0.2
 */
public class FetchInTheatersMoviesResultsReceiver extends BroadcastReceiver {

    /**
     * The action accepted by this receiver.
     */
    public static final String IN_THEATERS_M_DATA_ACTION = "action.intheaters.movies.list";

    /**
     * The key used by this receiver
     */
    public static final String IN_THEATERS_LIST_DATA_KEY = "repo.intheaters.list.data";


    public FetchInTheatersMoviesResultsReceiver() { }

    @Override
    public void onReceive(Context context, Intent intent) {
        List<Movie> inTheatersList = intent.getParcelableArrayListExtra(IN_THEATERS_LIST_DATA_KEY);
        YamdaApplication app =((YamdaApplication) context.getApplicationContext());

        //try to insert
        Uri uri = Repository.makeUri(DataProvider.THEATERS_TABLE_NAME);
        app.getRepository()
                .insertSetOfMovies(uri, inTheatersList);

        //alert for data available
        context.sendBroadcast(new Intent(AbstractSearchOptionsListFragment.DATA_AVAILABLE_ACTION));

        //ask for details when possible
        for (Movie m : inTheatersList) {
            ServicesUtils.askForDetails(app, m);
        }
    }
}
