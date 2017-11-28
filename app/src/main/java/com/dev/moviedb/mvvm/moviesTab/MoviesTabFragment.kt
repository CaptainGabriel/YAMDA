package com.dev.moviedb.mvvm.moviesTab

import android.os.Bundle
import com.dev.moviedb.mvvm.extensions.formatMovieCardName
import com.dev.moviedb.mvvm.extensions.loadUrl
import com.dev.moviedb.mvvm.fragments.AbstractDisplayFragment
import com.dev.moviedb.mvvm.repository.remote.TmdbApiProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.include_item_spotlight.*


/**
 * Tab fragment that shows the different information for movies inside the application.
 *
 * Yamda 1.0.0.
 */
class MoviesTabFragment : AbstractDisplayFragment() {

    override fun getLoggingTag(): String = this.javaClass.canonicalName

    /**
     * A reference to the view model class
     */
    private var viewModel: MoviesTabViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =  MoviesTabViewModel(TmdbApiProvider().getTmdbApiService())
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

}
