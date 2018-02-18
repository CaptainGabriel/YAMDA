package com.dev.moviedb.mvvm.activities

import android.os.Bundle
import android.support.v4.view.MenuCompat
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.widget.SearchView
import android.widget.Toast
import com.dev.moviedb.mvvm.adapters.MainOptionsPagerAdapter
import kotlinx.android.synthetic.main.activity_tab_options.*
import kotlinx.android.synthetic.main.toolbar_center_text.*
import petegabriel.com.yamda.R
import petegabriel.com.yamda.R.id.action_search


/**
 * Presents the tabs for the user to navigate between the main features of the app.
 */
class TabOptionsActivity : AppCompatActivity() {

    private val TAB_ONE = 0
    private val TAB_TWO = 1
    private val TAB_THREE = 2
    private val TAB_FOUR = 3


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_tab_options)
        /*
        The internal implementation of the support library just checks if the Toolbar has a title
        (not null) at the moment the SupportActionBar is set up.
        If there is, then this title will be used instead of the window title.
         */
        toolbar.title = ""
        setSupportActionBar(toolbar)

        //setup the pager
        pagerLayoutFrame.adapter = MainOptionsPagerAdapter(supportFragmentManager)

        setupTabLayout()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        /*val searchView = MenuItemCompat.getActionView(menu?.findItem(action_search)) as SearchView
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{

            override fun onQueryTextChange(newText: String?): Boolean {

                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {

                return true
            }
        })*/
        return true
    }

    private fun setupTabLayout() {
        //setup the tab layout
        tabLayoutFrame.setupWithViewPager(pagerLayoutFrame)

        tabLayoutFrame.getTabAt(TAB_ONE)?.icon = resources.getDrawable(R.drawable.ic_local_movies)
        tabLayoutFrame.getTabAt(TAB_TWO)?.icon = resources.getDrawable(R.drawable.ic_tv)
        tabLayoutFrame.getTabAt(TAB_THREE)?.icon = resources.getDrawable(R.drawable.ic_videocam)
        tabLayoutFrame.getTabAt(TAB_FOUR)?.icon = resources.getDrawable(R.drawable.ic_person_outline)
    }


}
