package com.example.dev.moviedb.activity;

import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.dev.moviedb.YamdaApplication;
import com.example.dev.moviedb.fragment.options.OnItemSelectedListener;
import com.example.dev.moviedb.model.async.CallResult;
import com.example.dev.moviedb.model.async.Completion;
import com.example.dev.moviedb.storage.repo.Repository;
import com.example.dev.moviedb.storage.repo.db.DataRecord;
import com.example.dev.moviedb.utils.NetworkUtils;
import com.example.dev.moviedb.utils.ToastUtils;
import com.example.dev.moviedb.views.adapters.SearchOptionsPagerAdapter;

import petegabriel.com.yamda.R;

/**
 * This class is the main activity of the application as of this moment (0.0.2).
 * <p/>
 * This class uses {@link PagerAdapter} that will provide
 * fragments for each of the sections. We use a
 * {@link FragmentPagerAdapter} derivative, which will keep every
 * loaded fragment in memory. If this becomes too memory intensive, it
 * may be best to switch to a
 * {@link FragmentStatePagerAdapter}.
 *
 * @version 0.0.2
 */
public class SearchOptionsActivity extends LoggingActivity
        implements OnItemSelectedListener<DataRecord> {

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_options_layout);

        initInstances();

        //restore state of query string
        if (savedInstanceState != null) {
            mSearchQueryState = savedInstanceState.getCharSequence(QUERY_SAVE_STATE_KEY);
        }
    }

    private void initInstances(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(new SearchOptionsPagerAdapter(getSupportFragmentManager()));

        //setup the tab layout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setOnTabSelectedListener(onSelectedTab());
        setupTabIcons(tabLayout);
    }

    /**
     * Put icons in each tab.
     */
    private void setupTabIcons(TabLayout tabLayout) {
        tabLayout.getTabAt(0)
                .setIcon(getResources().getDrawable(R.drawable.ic_local_movies_white_36dp));
        tabLayout.getTabAt(1).setIcon(getResources().getDrawable(R.drawable.ic_today_white_36dp));
        tabLayout.getTabAt(2).setIcon(getResources().getDrawable(R.drawable.ic_star_white_36dp));
    }

    @Override
    public void onItemSelected(String table, DataRecord obj) {
        long id = obj.getId();
        Log.d(TAG, "onItemSelected - #" + id);
        YamdaApplication app = ((YamdaApplication) getApplication());
        //offline mode
        Uri uri = Repository.makeUri(table);
        app.getRepository()
                .getRecordByID(ContentUris.withAppendedId(uri, id), id, handleSpecificMovieClickCallback());
    }

    @Override
    public void onItemSelected(DataRecord obj){
        throw new UnsupportedOperationException();
    }

    /**
     * The key used to save the state of the query text box.
     */
    public final String QUERY_SAVE_STATE_KEY = "QUERY";

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        CharSequence query = "";
        if (mSearchBar != null)
            query = mSearchBar.getQuery();
        Log.d("Search - OnSaveState", "Query: " + query);
        outState.putCharSequence(QUERY_SAVE_STATE_KEY, query);
    }

    /**
     * Reference to the search bar widget.
     */
    private SearchView mSearchBar;

    /**
     * Reference to the SearchView's content query.
     */
    private CharSequence mSearchQueryState;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_options_tab, menu);

        //prepare SearchView widget
        MenuItem menuItem = menu.findItem(R.id.search_bar);
        mSearchBar = (SearchView) MenuItemCompat.getActionView(menuItem);
        getControlOfSearchBar(mSearchBar);

        //if so restore state
        if (mSearchQueryState != null) {
            mSearchBar.setQuery(mSearchQueryState, false);
        }

        return true;
    }

    /**
     * It creates an anonymous implementation of OnQueryTextListener
     * in order to pass it to the SearchView element so it can listen
     * to queries sent by the user.
     * <p/>
     * It applies no filtering to the query received.
     *
     * @param sv SearchView element.
     */
    private void getControlOfSearchBar(SearchView sv) {
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //perform search
                String msg = getResources().getString(R.string.searching_by_keyword) + "\"" + query + "\"";
                Log.d(TAG, msg);

                //send query to the activity through the intent. SHe will know what to do with it
                Intent intentToChange =
                        new Intent(getApplicationContext(), SearchQueryOptionActivity.class);
                intentToChange.putExtra(SearchQueryOptionActivity.QUERY_STRING_KEY, query);

                //this option needs online access so don't waste resources if there is no connection
                //assumes WIFI type of connection but we could check user prefs for other types.
                if (NetworkUtils.isConnectedToWifi(getApplicationContext())) {
                    startActivity(intentToChange);
                } else {
                    String msgError = getResources().getString(R.string.fetching_info_from_server);
                    ToastUtils.showError(msgError, getApplicationContext());
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //text has changed, apply filtering ?
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.settings_option:
                startActivity(new Intent(getApplicationContext(), UserPreferencesActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * This method represents what to do when a movie is received after being
     * requested by the ID.
     */
    private Completion<DataRecord> handleSpecificMovieClickCallback() {
        return new Completion<DataRecord>() {
            @Override
            public void onResult(@NonNull CallResult<DataRecord> result) {
                try {
                    DataRecord movie = result.getResult();
                    startActivityForDetail(movie);
                } catch (Exception e) {
                    Log.d("OnItemSelected#onResult", e.getMessage());
                    String errorMsg = getResources().getString(R.string.cannot_show_the_detail);
                    ToastUtils.showShortMessage(errorMsg, getApplicationContext());
                }
            }
        };
    }


    /**
     * Utility method that starts a new DetailRecordActivity in order
     * to show the details of the item.
     *
     * @param recordClicked the item which details will be shown
     */
    private void startActivityForDetail(DataRecord recordClicked) {
        Log.d(TAG, "Starting an activity to display the details of the movie "
                + recordClicked.getOriginalTitle());
        Intent intentToMoveForward = new Intent(getApplicationContext(), DetailRecordActivity.class);
        //being parcelable guarantees cross-process storage
        startActivity(intentToMoveForward.putExtra(DetailRecordActivity.DETAILS_INTENT_ITEM_KEY, recordClicked));
    }

    /**
     * Provides the implementation of OnTabSelectedListener that handles
     * the selection of each tab.
     *
     * @return Anonymous implementation of OnTabSelectedListener
     */
    private TabLayout.OnTabSelectedListener onSelectedTab() {
        return new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        String inTheatersTabTitle = getResources().getString(R.string.tab_title_in_theaters);
                        getSupportActionBar().setTitle(inTheatersTabTitle);
                        break;
                    case 1:
                        String upcomingTabTitle = getResources().getString(R.string.tab_title_upcoming);
                        getSupportActionBar().setTitle(upcomingTabTitle);
                        break;
                    case 2:
                        String popularTabTitle = getResources().getString(R.string.tab_title_most_popular);
                        getSupportActionBar().setTitle(popularTabTitle);
                        break;
                }
                //we need to update the view with the next current fragment
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        };
    }
}
