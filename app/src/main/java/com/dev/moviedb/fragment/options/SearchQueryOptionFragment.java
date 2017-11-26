package com.dev.moviedb.fragment.options;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.dev.moviedb.YamdaApplication;
import com.dev.moviedb.mvvm.model.movies.Movie;
import com.dev.moviedb.mvvm.model.movies.MovieAggregator;
import com.dev.moviedb.model.async.CallResult;
import com.dev.moviedb.model.async.Completion;
import com.dev.moviedb.mvvm.model.movies.dto.MovieCollectionDto;
import com.dev.moviedb.views.adapters.QueryListAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;


/**
 * This class manages the list of items obtained through an online search based
 * upon given keywords.
 *
 * @version 1.0.0
 */
public class SearchQueryOptionFragment extends AbstractSearchOptionsListFragment<Movie> {


    /**
     * Holds the current amount of pages obtained via request.
     * It helps giving the illusion of unlimited results.
     */
    private int mCurrentPage;

    /**
     * The query made by the given keywords.
     */
    private String mQuery;

    /**
     * Indicates the distance to the last element that will trigger
     * a new search for the next page.
     */
    private int mTreshold;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        mCurrentPage = 1; //api says the first page starts at 1.
        mQuery = getArguments().getString(SAVED_PARCELABLE_KEY);

        setupListAdapter(savedInstanceState);
        loadElements();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    if (getListView().getLastVisiblePosition() >= getListView().getCount() - 1 - mTreshold) {
                        mCurrentPage++;
                        //load more list items
                        loadElements();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount) {
            }
        });
    }

    /**
     * The routine that requests one page of results based upon the current page.
     * This function is asynchronous.
     */
    private void loadElements(){
        YamdaApplication app = ((YamdaApplication) getActivity().getApplication());
    }

    /**
     * The request made by loadElements is asynchronous and this function is the callback
     * invoked when the results are made available.
     *
     * @see Completion
     *
     * @param appContext
     *      The application's context.
     * @return
     *      An instance of Completion.
     */
    private Completion<MovieAggregator> populateListCallback(final Context appContext) {
        return new Completion<MovieAggregator>() {
            @Override
            public void onResult(@NonNull CallResult<MovieAggregator> result) {
                try {
                    List<Movie> aggregator = result.getResult().getmResults();
                    updateAdapterFromLocalStorage(aggregator);
                } catch (Exception e) {
                    Log.d(TAG, "PopulateListCallback Method: " + e.getMessage());
                }
            }
        };
    }

    /**{@inheritDoc}*/
    @Override
    protected void setupListAdapter(Bundle savedInstanceState) {
        if (savedInstanceState != null)
            mItems = savedInstanceState.getParcelableArrayList(SAVE_ITEMS_STATE);
        else
            mItems = new ArrayList<>();

        setListAdapter(new QueryListAdapter(getActivity().getApplication(), mItems));
    }

    /**
     * This reference holds the async call made to the api
     * in order to be possible, if necessary, to cancel later during
     * the lifetime of this component.
     */
    private Call<MovieCollectionDto> mAsyncRequestData;

    /**{@inheritDoc}*/
    @Override
    protected void onNewData() {
        loadElements();
    }


    /**{@inheritDoc}*/
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Log.d(TAG, "Item clicked at position #" + position);

        //notify subscriber
        if (mClickedItemCallback != null) {
            mClickedItemCallback.onItemSelected(mItems.get(position));
        }
    }

    /**
     * Key used to save items when creating an instance of this fragment.
     */
    private static final String SAVED_PARCELABLE_KEY = "SearchQueryOptionFragment.new.instance.with.item";

    /**
     * Classes should invoke this method when want to create an instance of this fragment
     * as well as passing a parameter to it.
     * Parameters cannot be given through the ctor since the super class Fragment only invokes
     * the parameterless ctor. This is the pattern most used.
     */
    public static Fragment newInstance(String item) {
        Bundle args = new Bundle();
        args.putString(SAVED_PARCELABLE_KEY, item);
        SearchQueryOptionFragment frag = new SearchQueryOptionFragment();
        frag.setArguments(args);
        return frag;
    }

    /**{@inheritDoc}*/
    @Override
    public void onStop() {
        super.onStop();
        if(mAsyncRequestData != null)
            mAsyncRequestData.cancel();
    }

}
