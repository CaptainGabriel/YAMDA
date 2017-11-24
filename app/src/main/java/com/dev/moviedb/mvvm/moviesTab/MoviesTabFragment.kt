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

    /**
     * A reference to the view model class
     */
    private var viewModel: MoviesTabViewModel? = null

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =  MoviesTabViewModel(TmdbApiProvider().getTmdbApiService())
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_popular_info, container, false)

        configPopularRecViewAdapter(view)
        configTopRatedRecViewAdapter(view)

        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel?.findNowPlayingMoviesList()
                ?.subscribeOn(Schedulers.newThread())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ col ->
                    //TODO save List for when the user navigates to the NowPlayingMovies
                    spotlight_movie_image?.loadUrl(col.results[0].movieImages.backdropImagePath, false)
                    spotlight_movie_description?.text = col.results[0].primaryFacts.overview.formatMovieCardName(100)
                    spotlight_movie_rating?.text = "%.1f".format(col.results[0].popularity.voteAverage)
                    spotlight_movie_name?.text = col.results[0].primaryFacts.title
                },{
                    throwable -> handleError(throwable)
                })

        viewModel?.findMostPopularMovieList()
                ?.subscribeOn(Schedulers.newThread())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(addNewDataToPopularMoviesAdapter(), { throwable -> handleError(throwable) })

        viewModel?.findTopRatedMoviesList()
                ?.subscribeOn(Schedulers.newThread())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(addNewDataToAdapter(), { throwable -> handleError(throwable) })

    }

    /**
     * Send data to the adapter
     */
    private fun addNewDataToPopularMoviesAdapter(): (MovieCollectionDto) -> Unit {
        return { col: MovieCollectionDto ->
            (popularRecyclerView?.adapter as AbstractMovieItemAdapter).adNewData(col)
            (popularRecyclerView?.adapter as AbstractMovieItemAdapter).notifyDataSetChanged()
        }
    }

    /**
     * Send data to the adapter
     */
    private fun addNewDataToAdapter(): (MovieCollectionDto) -> Unit {
        return { col: MovieCollectionDto ->
            (topRatedRecyclerView?.adapter as AbstractMovieItemAdapter).adNewData(col)
            (topRatedRecyclerView?.adapter as AbstractMovieItemAdapter).notifyDataSetChanged()
        }
    }

    /**
     * Handle error after requesting data from ViewModel
     */
    private fun handleError(throwable: Throwable) {
        //TODO change in future version
        ToastUtils.showShortMessage(throwable.message!!.toString(), context)
    }

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
