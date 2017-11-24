package com.dev.moviedb.mvvm.userPreferences


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


/**
 * Fragment responsible for managing user preferences.
 */
class UserPreferencesFragment : Fragment() {

    /**
     * A reference to the view model class
     */
    private var viewModel: UserPrefsViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = UserPrefsViewModel()
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)

    }

}

