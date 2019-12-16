package com.dev.moviedb.mvvm.nowPlayingMoviesTab

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dev.moviedb.YamdaApplication
import com.dev.moviedb.mvvm.adapters.AbstractMovieItemAdapter
import com.dev.moviedb.mvvm.extensions.prependCallLocation
import com.dev.moviedb.mvvm.repository.NowPlayingMovieRepository
import com.dev.moviedb.mvvm.repository.remote.dto.MovieCollectionDTO
import com.dev.moviedb.mvvm.repository.remote.dto.MovieDTO
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import petegabriel.com.yamda.R

/**
 * This fragment displays the imageList currently in theaters in a grid layout.
 *
 *
 * Yamda 1.1.0.
 */
class NowPlayingMoviesTabFragment : Fragment()  {

    private var viewModel: NowPlayingMoviesTabViewModel? = null

    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val app = activity?.applicationContext as YamdaApplication
        val nowPlayingRepo = NowPlayingMovieRepository(app.apiService)
        viewModel = NowPlayingMoviesTabViewModel(nowPlayingRepo)
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                   savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_grid_nowplaying_movies_layout, container, false)

        recyclerView = view.findViewById(R.id.listRecyclerView)

        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = GridLayoutManager(activity, 2)
        recyclerView?.adapter = StaggeredMovieDisplayAdapter(handleItemClick())
        recyclerView?.itemAnimator = DefaultItemAnimator()

        return view
    }

    private fun handleItemClick(): (MovieDTO) -> Unit = {
        it -> viewModel?.findVideoDetailsForId(it.id)
            ?.subscribeOn(Schedulers.newThread())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({ t ->
                run {
                    val manager = fragmentManager
                    val videoPlayerFragDialog = VideoPlayerFragment.newInstance(t?.results?.get(0)?.key!!)
                    videoPlayerFragDialog.show(manager, "VideoPlayerFragDialog")
                }
            }, { throwable -> handleError(throwable) })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToNowPlayingMovies()
    }


    @SuppressLint("CheckResult")
    private fun subscribeToNowPlayingMovies() {
        viewModel?.findNowPlayingMovies()
                ?.subscribeOn(Schedulers.newThread())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ t: MovieCollectionDTO ->
                    (recyclerView?.adapter as AbstractMovieItemAdapter).addNewData(ArrayList(t.results))
                    (recyclerView?.adapter as AbstractMovieItemAdapter).notifyDataSetChanged()
                }, { throwable -> handleError(throwable) })
    }


    /**
     * Handle error after requesting data from ViewModel
     */
    private fun handleError(throwable: Throwable) = Log.d("NowPlayingTab", throwable.message!!.toString().prependCallLocation())


}