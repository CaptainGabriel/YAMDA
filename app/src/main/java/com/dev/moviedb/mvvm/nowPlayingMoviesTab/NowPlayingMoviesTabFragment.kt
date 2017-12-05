package com.dev.moviedb.mvvm.nowPlayingMoviesTab

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dev.moviedb.YamdaApplication
import com.dev.moviedb.mvvm.adapters.AbstractMovieItemAdapter
import com.dev.moviedb.mvvm.adapters.MovieDisplayAdapter
import com.dev.moviedb.mvvm.extensions.prependCallLocation
import com.dev.moviedb.mvvm.repository.NowPlayingMovieRepository
import com.dev.moviedb.mvvm.repository.remote.dto.MovieCollectionDTO
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import petegabriel.com.yamda.R
import timber.log.Timber

/**
 * This fragment displays the movies currently in theaters in a grid layout.
 *
 *
 * Yamda 1.0.0.
 */
class NowPlayingMoviesTabFragment : Fragment()  {

    private var viewModel: NowPlayingMoviesTabViewModel? = null

    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val app = activity.applicationContext as YamdaApplication
        val nowPlayingRepo = NowPlayingMovieRepository(app.apiService)
        viewModel = NowPlayingMoviesTabViewModel(nowPlayingRepo)
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                                   savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_grid_nowplaying_movies_layout, container, false)

        val tempView: View = view.findViewById(R.id.cardviewGridLayout)
        recyclerView = tempView.findViewById<RecyclerView>(R.id.listRecyclerView)

        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = GridLayoutManager(activity, 2)
        recyclerView?.adapter = MovieDisplayAdapter()
        recyclerView?.itemAnimator = DefaultItemAnimator()

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToNowPlayingMovies()
    }



    private fun subscribeToNowPlayingMovies() {
        viewModel?.findNowPlayingMovies()
                ?.subscribeOn(Schedulers.newThread())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ t: MovieCollectionDTO ->
                    (recyclerView?.adapter as AbstractMovieItemAdapter).adNewData(t.results)
                    (recyclerView?.adapter as AbstractMovieItemAdapter).notifyDataSetChanged()
                }, { throwable -> handleError(throwable) })
    }


    /**
     * Handle error after requesting data from ViewModel
     */
    private fun handleError(throwable: Throwable) = Timber.d(this.javaClass.canonicalName, throwable.message!!.toString().prependCallLocation())


}