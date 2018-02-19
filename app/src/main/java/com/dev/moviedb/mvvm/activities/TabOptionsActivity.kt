package com.dev.moviedb.mvvm.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import com.dev.moviedb.YamdaApplication
import com.dev.moviedb.mvvm.adapters.MainOptionsPagerAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_tab_options.*
import kotlinx.android.synthetic.main.toolbar_center_text.*
import petegabriel.com.yamda.R


/**
 * Presents the tabs for the user to navigate between the main features of the app.
 *
 * It handles the text query made via SearchView widget.
 */
class TabOptionsActivity : AppCompatActivity() {

    private val TAB_ONE = 0
    private val TAB_TWO = 1
    private val TAB_THREE = 2
    private val TAB_FOUR = 3

    private var viewModel: TabOptionsViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = TabOptionsViewModel((applicationContext as YamdaApplication).apiService)

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
        val searchMenuItem = menu?.findItem(R.id.action_search)
        setupSearchView(searchMenuItem!!)
        return true
    }

    private fun setupSearchView(item: MenuItem){
        val searchView = item.actionView as SearchView
        searchView.queryHint = resources.getString(R.string.action_search_query_hint)

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                //TODO log query statement
                query?.let{ getData(it) }
                return true
            }
        })
    }

    private fun getData(query: String): Disposable? {
        return viewModel?.query(query)
                ?.subscribeOn(Schedulers.newThread())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ t ->
                    run {
                        //TODO
                    }
                }, { throwable ->
                    //TODO handle error
                })
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
