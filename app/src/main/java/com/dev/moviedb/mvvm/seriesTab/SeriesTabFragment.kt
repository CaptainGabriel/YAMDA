package com.dev.moviedb.mvvm.seriesTab


import android.os.Bundle
import com.dev.moviedb.mvvm.fragments.AbstractDisplayFragment
import com.dev.moviedb.mvvm.repository.remote.TmdbApiProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * The ViewModel for the tab related with movie information
 *
 * Yamda 1.0.0
 */
class SeriesTabFragment : AbstractDisplayFragment() {

    override fun getLoggingTag(): String = this.javaClass.canonicalName

    /**
     * A reference to the view model class
     */
    private var viewModel: SeriesTabViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =  SeriesTabViewModel(TmdbApiProvider().getTmdbApiService())
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //airing today tv shows

        //most popular tv shows
        viewModel?.findPopularTvSeries()
                ?.subscribeOn(Schedulers.newThread())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe( { t -> addNewDataToPopularMoviesAdapter()(t.results) },
                             { throwable -> handleError(throwable) })

        //top rated tv shows
        viewModel?.findTopRatedTvSeries()
                ?.subscribeOn(Schedulers.newThread())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ t -> addNewDataToTopRatedMoviesAdapter()(t.results) }, { throwable -> handleError(throwable) })

    }

}
