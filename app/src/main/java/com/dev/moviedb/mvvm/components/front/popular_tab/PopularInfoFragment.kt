package com.dev.moviedb.mvvm.components.front.popular_tab

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dev.moviedb.model.dto.MovieCollectionDto
import com.dev.moviedb.mvvm.repository.remote.TmdbApiProvider
import com.dev.moviedb.utils.ToastUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import petegabriel.com.yamda.R


/**
 *
 *
 * Yamda 1.0.0.
 */
class PopularInfoFragment : Fragment() {

    private var service: TmdbApiProvider? = null

    private var popularMoviesListAdapter: PopularMoviesListAdapter? = null
    private var topRatedMoviesListAdapter: TopRatedMoviesListAdapter? = null

    var popularRecyclerView: RecyclerView? = null
    var topRatedRecyclerView: RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater!!.inflate(R.layout.fragment_popular_info, container, false)

        popularMoviesListAdapter = PopularMoviesListAdapter()
        topRatedMoviesListAdapter = TopRatedMoviesListAdapter()

        popularRecyclerView = view.findViewById(R.id.popularRecyclerView)
        topRatedRecyclerView = view.findViewById(R.id.topRatedRecyclerView)

        configPopularRecViewAdapter()
        configTopRatedRecViewAdapter()


        return view
    }

    private fun configPopularRecViewAdapter() {
        popularRecyclerView?.setHasFixedSize(true)
        popularRecyclerView?.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        popularRecyclerView?.adapter = popularMoviesListAdapter
        popularRecyclerView?.itemAnimator = DefaultItemAnimator()
    }

    private fun configTopRatedRecViewAdapter() {
        topRatedRecyclerView?.setHasFixedSize(true)
        topRatedRecyclerView?.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        topRatedRecyclerView?.adapter = topRatedMoviesListAdapter
        topRatedRecyclerView?.itemAnimator = DefaultItemAnimator()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        service?.getTmdbApiService()
                ?.findMostPopularMovies()
                ?.subscribeOn(Schedulers.newThread())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(
                        { col: MovieCollectionDto ->
                            (popularRecyclerView?.adapter as AbstractMovieItemAdapter).adNewData(col)
                            (popularRecyclerView?.adapter as AbstractMovieItemAdapter).notifyDataSetChanged()
                        },
                        { trowable -> ToastUtils.showShortMessage(trowable.message!!.toString(), context)})

        service?.getTmdbApiService()
                ?.findTopRatedmovies()
                ?.subscribeOn(Schedulers.newThread())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(
                        { col: MovieCollectionDto ->
                            (topRatedRecyclerView?.adapter as AbstractMovieItemAdapter).adNewData(col)
                            (topRatedRecyclerView?.adapter as AbstractMovieItemAdapter).notifyDataSetChanged()
                        },
                        { trowable -> ToastUtils.showShortMessage(trowable.message!!.toString(), context)})
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        service = TmdbApiProvider()
    }


}
