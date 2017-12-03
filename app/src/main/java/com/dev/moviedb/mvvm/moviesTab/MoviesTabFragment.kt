package com.dev.moviedb.mvvm.moviesTab

import android.os.Bundle
import com.dev.moviedb.YamdaApplication
import com.dev.moviedb.mvvm.extensions.formatMovieCardName
import com.dev.moviedb.mvvm.extensions.loadUrl
import com.dev.moviedb.mvvm.fragments.AbstractDisplayFragment
import com.dev.moviedb.mvvm.repository.NowPlayingMovieRepository
import com.dev.moviedb.mvvm.repository.PopularMovieRepository
import com.dev.moviedb.mvvm.repository.TopRatedMovieRepository
import com.dev.moviedb.mvvm.repository.remote.dto.MovieCollectionDTO
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
        val app = activity.applicationContext as YamdaApplication
        var popularRepo = PopularMovieRepository(app.apiService)
        var topRatedRepo = TopRatedMovieRepository(app.apiService)
        var nowPlayingRepo = NowPlayingMovieRepository(app.apiService)
        viewModel =  MoviesTabViewModel(app.apiService, popularRepo, topRatedRepo, nowPlayingRepo)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel?.getMostRecentMovie()
                ?.subscribeOn(Schedulers.newThread())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    t ->
                    run {
                        t.backdropPath?.let { spotlight_movie_image?.loadUrl(it, false) }
                        spotlight_movie_description?.text = t.overview.formatMovieCardName(100)
                        spotlight_movie_rating?.text = "%.1f".format(t.voteAverage)
                        spotlight_movie_name?.text = t.title
                    }
                }, { throwable ->
                    handleError(throwable)
                })

        subscribeToNowPlayingMovies()

        subscribeToMostPopularMovies()

        subscribeToTopRatedMovies()

    }

    private fun subscribeToTopRatedMovies() {
        viewModel?.findNowPlayingMoviesList()
                ?.subscribeOn(Schedulers.newThread())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ col ->
                    addNewDataToTopRatedMoviesAdapter()(col.results)
                }, { throwable ->
                    handleError(throwable)
                })
    }

    private fun subscribeToMostPopularMovies() {
        viewModel?.findMostPopularMovieList()
                ?.subscribeOn(Schedulers.newThread())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ t: MovieCollectionDTO ->
                    addNewDataToPopularMoviesAdapter()(t.results)
                }, { t: Throwable? ->
                    handleError(t!!)
                })
    }

    private fun subscribeToNowPlayingMovies() {
        viewModel?.findNowPlayingMoviesList()
         ?.subscribeOn(Schedulers.newThread())
         ?.observeOn(AndroidSchedulers.mainThread())
         ?.subscribe({ t: MovieCollectionDTO ->
             addNewDataNowPlayingMoviesAdapter()(t.results)
         }, { throwable -> handleError(throwable) })
    }

}
