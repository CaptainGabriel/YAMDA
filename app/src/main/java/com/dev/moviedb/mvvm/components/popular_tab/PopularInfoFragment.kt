package com.dev.moviedb.acomponents.popular_tab


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import petegabriel.com.yamda.R


/**
 * A simple [Fragment] subclass.
 */
class PopularInfoFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_popular_info, container, false)
    }

}// Required empty public constructor