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

    private val TAB_ONE = 0
    private val TAB_TWO = 1
    private val TAB_THREE = 2
    private val TAB_FOUR = 3


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_tab_options)
        setSupportActionBar(toolbar)

        //setup the pager
        featuresAdapter = MainOptionsPagerAdapter(supportFragmentManager)
        pagerLayoutFrame.adapter = featuresAdapter

        //setup the tab layout
        tabLayoutFrame.setupWithViewPager(pagerLayoutFrame)

        tabLayoutFrame.getTabAt(TAB_ONE)?.icon = resources.getDrawable(R.drawable.ic_local_movies)
        tabLayoutFrame.getTabAt(TAB_TWO)?.icon = resources.getDrawable(R.drawable.ic_tv)
        tabLayoutFrame.getTabAt(TAB_THREE)?.icon = resources.getDrawable(R.drawable.ic_videocam)
        tabLayoutFrame.getTabAt(TAB_FOUR)?.icon = resources.getDrawable(R.drawable.ic_person_outline)



    }


    override fun onStart() {
        super.onStart()
        supportActionBar?.title = "Yamda"
    }



}
