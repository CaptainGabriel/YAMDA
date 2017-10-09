package com.dev.moviedb.activity;


import android.os.Parcelable;
import android.support.v4.app.Fragment;

import com.dev.moviedb.fragment.DetailMovieFragment;

import petegabriel.com.yamda.R;

public class DetailMovieActivity extends SingleFragmentActivity {

    public final static String DETAILS_INTENT_ITEM_KEY = "DetailMovieActivity.item.object.clicked";

    /**{@inheritDoc}*/
    @Override
    protected Fragment createFragment() {
        Parcelable item = getIntent().getParcelableExtra(DETAILS_INTENT_ITEM_KEY);
        return DetailMovieFragment.newInstance(item);
    }

    /**{@inheritDoc}*/
    @Override
    protected void setupContentView() {
        setContentView(R.layout.activity_data_details_layout);
    }

    /**{@inheritDoc}*/
    @Override
    protected int getFrameContainer() {
        return R.id.details_frame_container;
    }



}
