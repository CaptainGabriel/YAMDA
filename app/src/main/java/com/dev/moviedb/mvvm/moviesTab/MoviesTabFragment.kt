package com.dev.moviedb.mvvm.moviesTab

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.dev.moviedb.model.dto.MovieCollectionDto
import com.dev.moviedb.mvvm.adapters.AbstractMovieItemAdapter
import com.dev.moviedb.mvvm.extensions.formatMovieCardName
import com.dev.moviedb.mvvm.extensions.loadUrl
import com.dev.moviedb.mvvm.repository.remote.TmdbApiProvider
import com.dev.moviedb.utils.ToastUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_popular_info.*
import petegabriel.com.yamda.R


/**
 * Tab fragment that shows the different information for movies inside the application.
 *
 * Yamda 1.0.0.
 */
class MoviesTabFragment : Fragment() {

    private var viewModel: MoviesTabViewModel? = null

    private var popularMoviesListAdapter: PopularMoviesListAdapter? = null
    private var topRatedMoviesListAdapter: TopRatedMoviesListAdapter? = null

    var popularRecyclerView: RecyclerView? = null
    var topRatedRecyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =  MoviesTabViewModel(TmdbApiProvider().getTmdbApiService())
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_popular_info, container, false)

        popularMoviesListAdapter = PopularMoviesListAdapter()
        topRatedMoviesListAdapter = TopRatedMoviesListAdapter()

        configPopularRecViewAdapter(view)
        configTopRatedRecViewAdapter(view)


        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /*service?.getTmdbApiService()
                ?.findLatestMovie()
                ?.subscribeOn(Schedulers.newThread())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(
                        { col: MovieDTO ->
                            spotlight_movie_image?.loadUrl(col.imageBackdropPath)
                            spotlight_movie_description?.text = col.overview
                        },
                        { trowable -> ToastUtils.showShortMessage(trowable.message!!.toString(), context)})

                        */

        viewModel?.getMostPopularMovieList()
                ?.subscribeOn(Schedulers.newThread())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(
                        { col: MovieCollectionDto ->
                            (popularRecyclerView?.adapter as AbstractMovieItemAdapter).adNewData(col)
                            (popularRecyclerView?.adapter as AbstractMovieItemAdapter).notifyDataSetChanged()

                            spotlight_movie_image?.loadUrl(col.results[0].movieImages.backdropImagePath)
                            spotlight_movie_description?.text = col.results[0].primaryFacts.overview.formatMovieCardName(100)
                            spotlight_movie_rating?.text = "%.1f".format(col.results[0].popularity.voteAverage)
                            spotlight_movie_name?.text = col.results[0].primaryFacts.title
                        },
                        { throwable -> handleError(throwable) })

        viewModel?.TopRatedMoviesList()
                ?.subscribeOn(Schedulers.newThread())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(addNewDataToAdapter(), { throwable -> handleError(throwable) })

    }

    private fun addNewDataToAdapter(): (MovieCollectionDto) -> Unit {
        return { col: MovieCollectionDto ->
            (topRatedRecyclerView?.adapter as AbstractMovieItemAdapter).adNewData(col)
            (topRatedRecyclerView?.adapter as AbstractMovieItemAdapter).notifyDataSetChanged()
        }
    }

    private fun handleError(throwable: Throwable) {
        //TODO change in future version
        ToastUtils.showShortMessage(throwable.message!!.toString(), context)
    }


    private fun configPopularRecViewAdapter(view: View) {

        val tempView: View = view.findViewById(R.id.popularCardView)
        tempView.findViewById<TextView>(R.id.cardviewDescriptionText).text = getString(R.string.popular_card_title)
        popularRecyclerView = tempView.findViewById(R.id.movieListRecyclerView)

        popularRecyclerView?.setHasFixedSize(true)
        popularRecyclerView?.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        popularRecyclerView?.adapter = popularMoviesListAdapter
        popularRecyclerView?.itemAnimator = DefaultItemAnimator()
    }

    private fun configTopRatedRecViewAdapter(view: View) {
        val tempView = view.findViewById<View>(R.id.topRatedCardView)
        tempView.findViewById<TextView>(R.id.cardviewDescriptionText).text = getString(R.string.top100_card_title)
        topRatedRecyclerView = tempView.findViewById(R.id.movieListRecyclerView)

        topRatedRecyclerView?.setHasFixedSize(true)
        topRatedRecyclerView?.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        topRatedRecyclerView?.adapter = topRatedMoviesListAdapter
        topRatedRecyclerView?.itemAnimator = DefaultItemAnimator()
    }

}
