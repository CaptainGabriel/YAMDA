package com.dev.moviedb.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.dev.moviedb.storage.repo.messenger.OnPreferencesChangeReceiver;
import com.dev.moviedb.utils.PreferencesUtils;

import petegabriel.com.yamda.R;

/**
 * The activity that provides support to the user in order be able to specify some
 * configurations related to the behavior of the application.
 *
 * @version 0.0.2
 */
public class UserPreferencesActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    /**
     * Use this constant every time you need to say "invalid time read go figure it out!"
     * somewhere in your code.
     */
    public static final long INVALID_TIME_INTERVAL = -1;

    /**
     * The key used to get the value of connection type preferences option.
     */
    public static final String TYPE_OF_CONNECTION_KEY = "type_connection_list_preference";

    /**
     * The key used to get the value representing the decision about obsolete data after some
     * device's configuration changes happen.
     */
    public static final String OBSOLETE_DATA_DECISION_KEY = "erase_data_on_configuration_changes";

    /**
     * The key used to get the value of the time interval used between data updates.
     */
    public static final String DATA_UPDATES_INTERVAL_KEY = "update_interval_preference";


    /**
     * Called when a shared preference is changed, added, or removed.
     * Gets called internally by the framework by the time Editor.commit() executes.
     *
     * @param sharedPreferences
     *      The set of preferences that results from the actions made inside the PreferenceFragment.
     * @param key
     *      The key specified in each preference type inside the xml preferences layout
     *
     */
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        //TODO Log.d(TAG, "Preferences changed - Key " + key);
        if (key.equals(UserPreferencesActivity.DATA_UPDATES_INTERVAL_KEY)) {
            long interval = PreferencesUtils.getUpdateInterval(sharedPreferences);
            //TODO Log.d(TAG, "New time interval : " + interval);
            Intent newMsg = new Intent().setAction(OnPreferencesChangeReceiver.ON_PREFERENCES_CHANGE_NOTIFICATION);
            newMsg.putExtra(OnPreferencesChangeReceiver.ON_PREFERENCES_CHANGE_NOTIFICATION_KEY, interval);
            sendBroadcast(newMsg);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).
        registerOnSharedPreferenceChangeListener(this);

        getSupportActionBar().setTitle(R.string.settings_action_bar_title);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new UserPreferenceFragment()).commit();
    }


    /**
     * A simple {@link Fragment} subclass.
     *
     * If this Fragment innerclass was non-static you'd always hold a reference to the parent Activity.
     * The GarbageCollector cannot collect your Activity that way.
     * So you can 'leak' the Activity if for example the orientation changes because the Fragment
     * might still live and gets inserted in a new Activity and continues to hold the reference.
     */
    public static class UserPreferenceFragment extends PreferenceFragment {


        public UserPreferenceFragment() {
            // Required empty public constructor
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.preferences);
        }
    }
}
