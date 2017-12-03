package com.dev.moviedb.mvvm.moviesTab

import android.os.Bundle
import com.dev.moviedb.mvvm.fragments.AbstractDisplayFragment
import com.dev.moviedb.mvvm.repository.PopularMovieRepository
import com.dev.moviedb.mvvm.repository.remote.TmdbApiProvider
import com.dev.moviedb.mvvm.repository.remote.dto.MovieCollectionDTO
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


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
        var repo = PopularMovieRepository(TmdbApiProvider().getTmdbApiService())
        viewModel =  MoviesTabViewModel(repo)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
/*
        viewModel?.findNowPlayingMoviesList()
                ?.subscribeOn(Schedulers.newThread())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ col ->
                    //TODO save List for when the user navigates to the NowPlayingMovies
                    spotlight_movie_image?.loadUrl(col.results[0].movieImages.backdropImagePath, false)
                    spotlight_movie_description?.text = col.results[0].primaryFact.overview.formatMovieCardName(100)
                    spotlight_movie_rating?.text = "%.1f".format(col.results[0].popularity.voteAverage)
                    spotlight_movie_name?.text = col.results[0].primaryFact.title
                },{
                    throwable -> handleError(throwable)
                })
*/
        viewModel?.findMostPopularMovieList()
                ?.subscribeOn(Schedulers.newThread())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    t: MovieCollectionDTO ->
                        addNewDataToPopularMoviesAdapter()(t.results)
                }, {
                    t: Throwable? -> handleError(t!!)
                })
/*
        viewModel?.findTopRatedMoviesList()
                ?.subscribeOn(Schedulers.newThread())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(addNewDataToAdapter(), { throwable -> handleError(throwable) })
*/
    }

}
