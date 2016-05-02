package com.example.dev.moviedb.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Class that provides common behavior for those who intend to host
 * just one fragment.
 *
 * @version 0.0.1
 */
public abstract class SingleFragmentActivity extends LoggingActivity {

    /**
     * Override this method in order to instantiate a specific
     * implementation of a fragment.
     */
    protected abstract Fragment createFragment();

    /**
     * Override this method to specify the
     * layout of the activity.
     */
    protected abstract void setupContentView();

    /**
     * Child classes must implement this method in order to return
     * the id of their frame layout.
     * @return
     *  The id of the frame layout
     */
    protected abstract int getFrameContainer();

    /**{@inheritDoc}*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setupContentView();

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragmentRef = fm.findFragmentById(getFrameContainer());

        if(fragmentRef == null){
            fragmentRef = createFragment();

            //Fragment transactions are used to
            //add, remove, attach, detach, or replace fragments in the fragment list.

            fm.beginTransaction()
                    .add(getFrameContainer(), fragmentRef)
                    .commit();

        }

    }


}