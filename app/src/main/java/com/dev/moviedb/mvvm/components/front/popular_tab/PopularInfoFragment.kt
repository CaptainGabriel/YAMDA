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
 * A simple [Fragment] subclass.
 */
class PopularInfoFragment : Fragment() {

    private var service: TmdbApiProvider? = null

    private var mAdapter: PopularMoviesListAdapter? = null

    var mRView: RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater!!.inflate(R.layout.fragment_popular_info, container, false)

        mAdapter = PopularMoviesListAdapter()

        mRView = view.findViewById(R.id.popularRecyclerView)
        mRView?.setHasFixedSize(true)
        mRView?.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        mRView?.adapter = mAdapter
        mRView?.itemAnimator = DefaultItemAnimator()

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        service?.getTmdbApiService()
                ?.findMostPopularMovies()
                ?.subscribeOn(Schedulers.newThread())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(
                        { col: MovieCollectionDto ->

                            ToastUtils.showShortMessage("OK", context)

                            (mRView?.adapter as PopularMoviesListAdapter).adNewData(col)
                            (mRView?.adapter as PopularMoviesListAdapter).notifyDataSetChanged()

                        },
                        { trowable -> ToastUtils.showShortMessage(trowable.message!!.toString(), context)})
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        service = TmdbApiProvider()
    }




}// Required empty public constructor
