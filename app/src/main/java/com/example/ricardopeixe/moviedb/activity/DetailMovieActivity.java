package com.example.ricardopeixe.moviedb.activity;

import com.example.ricardopeixe.moviedb.R;
import com.example.ricardopeixe.moviedb.fragment.DetailMovieFragment;

import android.os.Parcelable;
import android.support.v4.app.Fragment;

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
