package com.dev.moviedb.activity;

import android.os.Parcelable;
import android.support.v4.app.Fragment;

import com.dev.moviedb.fragment.DetailRecordFragment;

import petegabriel.com.yamda.R;

/**
 * Activity responsible for presenting the details of a certain movie.
 *
 * @version 0.0.1
 */
public class DetailRecordActivity extends SingleFragmentActivity {


    public final static String DETAILS_INTENT_ITEM_KEY = "DetailRecordActivity.item.object.clicked";

    /**{@inheritDoc}*/
    @Override
    protected Fragment createFragment() {
        Parcelable item = getIntent().getParcelableExtra(DETAILS_INTENT_ITEM_KEY);
        return DetailRecordFragment.newInstance(item);
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
