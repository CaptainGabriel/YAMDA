package com.dev.moviedb.mvvm.userPreferences


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import kotlinx.android.synthetic.main.fragment_user_preferences_layout.*
import petegabriel.com.yamda.R


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

        var view = inflater?.inflate(R.layout.fragment_user_preferences_layout, container, false)

        return view
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        simcard_preference_switch.setOnCheckedChangeListener(handleSimcardOptionChange())
        wifi_preference_switch.setOnCheckedChangeListener(handleNetworkOptionChange())

    }

    /**
     * Write the simcard option change into preferences file.
     */
    private fun handleSimcardOptionChange(): (CompoundButton, Boolean) -> Unit {
        return {
            _: CompoundButton, isChecked: Boolean ->
            Log.d("SimcardSwitch", "Simcard checked: $isChecked")
            if (isChecked) {
               //TODO save it into preferences file
            }
        }
    }

    /**
     * Write the network option change into preferences file.
     */
    private fun handleNetworkOptionChange(): (CompoundButton, Boolean) -> Unit {
        return {
            _: CompoundButton, isChecked: Boolean ->
            Log.d("NetworkSwitch", "Network checked: $isChecked")
            if (isChecked) {
                //TODO save it into preferences file
            }
        }
    }

}

