package com.example.dev.moviedb.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.example.dev.moviedb.YamdaApplication;
import com.example.dev.moviedb.fragment.options.OnItemSelectedListener;
import com.example.dev.moviedb.fragment.options.SearchQueryOptionFragment;
import com.example.dev.moviedb.model.Movie;
import com.example.dev.moviedb.model.async.CallResult;
import com.example.dev.moviedb.model.async.Completion;
import com.example.dev.moviedb.utils.ToastUtils;

import petegabriel.com.yamda.R;

/**
 * The activity that provides a view with the results of a certain query in the
 * searchview widget.
 *
 * @version 1.0.0
 */
public class SearchQueryOptionActivity extends SingleFragmentActivity
        implements OnItemSelectedListener<Movie> {

    /**
     * Use this key when sending information through intents to this activity.
     * Its suppose to receive the query string the user just entered inside the search view located
     * at {@link SearchOptionsActivity}.
     */
    public static final String QUERY_STRING_KEY = "SearchQueryOptionActivity.expected-query.string";

    @Override
    protected Fragment createFragment() {
        String query = getIntent().getStringExtra(QUERY_STRING_KEY);
        return SearchQueryOptionFragment.newInstance(query);
    }

    @Override
    protected void setupContentView() {
        setTitle(getResources().getString(R.string.details_label));
        setContentView(R.layout.activity_search_query_option_layout);
    }

    @Override
    protected int getFrameContainer() {
        return R.id.query_results_container;
    }

    /**
     * If an item was found, display its details in the proper activity.
     */
    private Completion<Movie> handleSpecificMovieCallback() {
        return new Completion<Movie>() {
            @Override
            public void onResult(@NonNull CallResult<Movie> result) {
                try {
                    Movie movie = result.getResult();
                    Intent intentToMoveForward = new Intent(getApplicationContext(), DetailMovieActivity.class);
                    //being parcelable guarantees cross-process storage
                    intentToMoveForward.putExtra(DetailMovieActivity.DETAILS_INTENT_ITEM_KEY, movie);
                    startActivity(intentToMoveForward);
                } catch (Exception e) {
                    Log.e("OnItemSelected#onResult", e.getMessage());
                    String msg = getApplicationContext().getResources().getString(R.string.cannot_show_the_detail);
                    ToastUtils.showShortMessage(msg, getApplicationContext());
                }
            }
        };
    }

    @Override
    public void onItemSelected(String table, Movie obj) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void onItemSelected(Movie obj) {
        long id = obj.getPrimaryFacts().getId();
        Log.d(TAG, "onItemSelected - #" + id);
        YamdaApplication app = ((YamdaApplication) getApplication());

        // this function only supports online mode.
        String appendToResult = "trailers";
        app.getApiFetcher()
                .findMovieByIdAsync(id, app.getCurrentAppLanguage(), appendToResult, handleSpecificMovieCallback());
    }

}
