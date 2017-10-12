package com.dev.moviedb.acomponents.tabs_layout

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_tab_options.*
import petegabriel.com.yamda.R


/**
 * Presents the tabs for the user to navigate between the main features of the app.
 */
class TabOptionsActivity : AppCompatActivity() {

    private var featuresAdapter: MainOptionsPagerAdapter? = null

    private val titles = listOf("Movies","Tv Series","Account")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_tab_options)

        //setup the pager
        featuresAdapter = MainOptionsPagerAdapter(supportFragmentManager)
        pagerLayoutFrame.adapter = featuresAdapter

        //setup the tab layout
        tabLayoutFrame.setupWithViewPager(pagerLayoutFrame)

        tabLayoutFrame.getTabAt(0)?.text = titles[0]
        tabLayoutFrame.getTabAt(1)?.text = titles[1]
        tabLayoutFrame.getTabAt(2)?.text = titles[2]
    }




}
