package com.dev.moviedb.views.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.dev.moviedb.fragment.options.InTheatersSearchFragment;
import com.dev.moviedb.fragment.options.MostPopularSearchFragment;
import com.dev.moviedb.fragment.options.UpcomingSearchFragment;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SearchOptionsPagerAdapter extends FragmentPagerAdapter {

    public SearchOptionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new InTheatersSearchFragment();
            case 1:
                return new UpcomingSearchFragment();
            case 2:
                return new MostPopularSearchFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // return null to display only the icon
        return null;
    }




}