package com.dev.moviedb.mvvm.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.dev.moviedb.mvvm.adapters.AbstractMovieItemAdapter
import com.dev.moviedb.mvvm.extensions.prependCallLocation
import com.dev.moviedb.mvvm.moviesTab.PopularMoviesListAdapter
import com.dev.moviedb.mvvm.moviesTab.TopRatedMoviesListAdapter
import com.dev.moviedb.mvvm.repository.remote.dto.MovieDTO
import petegabriel.com.yamda.R

/**
 * This class contains common behavior used to display
 * lists of movies/series.
 *
 * Yamda 1.0.0.
 */
abstract class AbstractDisplayFragment : Fragment() {


    /**
     * A reference to the RecyclerView widget used to display the most
     * popular movies.
     */
    private var popularRecyclerView: RecyclerView? = null

    /**
     * A reference to the RecyclerView widget used to display the most
     * top rated movies.
     */
    private var topRatedRecyclerView: RecyclerView? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_movies_tab_layout, container, false)

        configPopularRecViewAdapter(view)
        configTopRatedRecViewAdapter(view)

        return view
    }


    /**
     * Send data to the adapter
     */
    protected fun addNewDataToPopularMoviesAdapter(): (List<MovieDTO>?) -> Unit {
        return { col: List<MovieDTO>? ->
            (popularRecyclerView?.adapter as AbstractMovieItemAdapter).adNewData(col)
            (popularRecyclerView?.adapter as AbstractMovieItemAdapter).notifyDataSetChanged()
        }
    }

    /**
     * Send data to the adapter
     */
    protected fun addNewDataToAdapter(): (List<MovieDTO>?) -> Unit {
        return { col: List<MovieDTO>? ->
            (topRatedRecyclerView?.adapter as AbstractMovieItemAdapter).adNewData(col)
            (topRatedRecyclerView?.adapter as AbstractMovieItemAdapter).notifyDataSetChanged()
        }
    }

    /**
     * Handle error after requesting data from ViewModel
     */
    protected fun handleError(throwable: Throwable) = Log.d(getLoggingTag(), throwable.message!!.toString().prependCallLocation())


    abstract fun getLoggingTag(): String

    /**
     * Configuration of the recyclerview's adapter
     */
    private fun configPopularRecViewAdapter(view: View) {

        val tempView: View = view.findViewById(R.id.popularCardView)
        tempView.findViewById<TextView>(R.id.cardviewDescriptionText).text = getString(R.string.popular_card_title)
        popularRecyclerView = tempView.findViewById(R.id.movieListRecyclerView)

        popularRecyclerView?.setHasFixedSize(true)
        popularRecyclerView?.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        popularRecyclerView?.adapter = PopularMoviesListAdapter()
        popularRecyclerView?.itemAnimator = DefaultItemAnimator()
    }

    /**
     * Configuration of the recyclerview's adapter
     */
    private fun configTopRatedRecViewAdapter(view: View) {
        val tempView = view.findViewById<View>(R.id.topRatedCardView)
        tempView.findViewById<TextView>(R.id.cardviewDescriptionText).text = getString(R.string.top100_card_title)
        topRatedRecyclerView = tempView.findViewById(R.id.movieListRecyclerView)

        topRatedRecyclerView?.setHasFixedSize(true)
        topRatedRecyclerView?.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        topRatedRecyclerView?.adapter = TopRatedMoviesListAdapter()
        topRatedRecyclerView?.itemAnimator = DefaultItemAnimator()
    }
}