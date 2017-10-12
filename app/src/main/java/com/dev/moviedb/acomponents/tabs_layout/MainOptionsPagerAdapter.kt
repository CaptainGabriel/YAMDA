package com.dev.moviedb.acomponents.tabs_layout

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.dev.moviedb.acomponents.movies_tab.MoviesInfoFragment
import com.dev.moviedb.acomponents.series_tab.SeriesTvInfoFragment
import com.dev.moviedb.acomponents.user_acount_tab.UserAccountInfoFragment

/**
 * The adapter used to resolve the fragments that
 * the user can navigate through.
 *
 * @see{FragmentPagerAdapter}
 *
 * @author PeteGabriel on 12/10/2017
 * Copyright (c) 2017
 * All rights reserved.
 */
class MainOptionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    //The amount fo fragments managed by this adapter.
    private val TOTAL_FRAGS = 3

    private val titles = listOf("Movies","Tv Series","Account")

    override fun getItem(position: Int): Fragment =
            when(position){
                0 -> MoviesInfoFragment()
                1 -> SeriesTvInfoFragment()
                2 -> UserAccountInfoFragment()
                else -> MoviesInfoFragment()
            }

    override fun getPageTitle(position: Int): CharSequence {
        var a = titles[position]
        return a
    }

    override fun getCount(): Int = TOTAL_FRAGS



}