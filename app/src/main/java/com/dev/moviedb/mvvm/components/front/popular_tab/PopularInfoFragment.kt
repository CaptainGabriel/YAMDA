package com.dev.moviedb.mvvm.components.front.popular_tab


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_popular_info, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        service = TmdbApiProvider()

        service?.getTmdbApiService()
                ?.findMostPopularMovies()
                ?.subscribeOn(Schedulers.newThread())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(
                        { ToastUtils.showShortMessage("OK", context) },
                        { trowable -> ToastUtils.showShortMessage(trowable.message!!.toString(), context)})
    }

}// Required empty public constructor
