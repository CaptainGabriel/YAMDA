package com.dev.moviedb.fragment.options;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;

import com.dev.moviedb.fragment.LifecycleLogListFragment;
import com.dev.moviedb.mvvm.modelstate.movies.content.Identifiable;
import com.dev.moviedb.model.async.CallResult;
import com.dev.moviedb.model.async.Completion;
import com.dev.moviedb.utils.ToastUtils;
import com.dev.moviedb.utils.Utils;
import com.dev.moviedb.views.adapters.AbstractListAdapter;

import java.util.ArrayList;
import java.util.List;

import petegabriel.com.yamda.R;

/**
 * Represents the common behavior between list fragments.
 */
public abstract class AbstractSearchOptionsListFragment<T extends Identifiable & Parcelable> extends LifecycleLogListFragment {

    /**
     * Tag for debug purposes.
     */
    protected final String TAG = Utils.makeLogTag(getClass());

    /**
     * Reference to the list of items pf each instance.
     */
    protected List<T> mItems;


    protected final String SAVE_ITEMS_STATE = "moviedb.fragment.options.save.items.state";

    /**
     * Reference to notify the activity.
     */
    protected OnItemSelectedListener mClickedItemCallback;

    /**
     * This receiver is managed during the lifecycle of the fragment instance.
     * Being receptive to present (new) data is only good when we are actually ready
     * to do so. Registering their interest during their lifecycle might mean messages have passed
     * by without any noticing from the fragment.
     */
    protected BroadcastReceiver mDataAvailableReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            onNewData();
        }
    };

    /**
     * This receiver is managed during the lifecycle of the fragment instance.
     * It lets the component be aware of messages that indicate the repo was cleared,
     * it holds no records.
     */
    protected BroadcastReceiver mDataErasedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            onErasedData();
        }
    };

    /**
     * An action capable of being handled by the receiver.
     */
    public static final String DATA_AVAILABLE_ACTION = "com.moviedb.fragment.options.data.available.action";

    /**
     * An action capable of being handled by the receiver.
     */
    public static final String DATA_ERASED_ACTION = "com.moviedb.fragment.options.data.not.available.action";


    /**
     * This method provides the implementation of the actions each fragment
     * would like to perform when new data comes into the local storage.
     */
    protected abstract void onNewData();

    /**
     * This method is invoked when the repository has no data to give back.
     * Child classes should override this method to handle situations like those.
     * The default behavior is to update the adapter to display none.
     */
    protected void onErasedData(){
        Log.d(TAG, "OnErasedData Invoked");
        AbstractListAdapter adapter = (AbstractListAdapter) getListAdapter();
        adapter.clear();
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "OnCreate");
        setRetainInstance(true);

        //setup the adapter first than anything
        setupListAdapter(savedInstanceState);

        //listen for messages
        getActivity().registerReceiver(mDataAvailableReceiver,
                new IntentFilter(DATA_AVAILABLE_ACTION));
        getActivity().registerReceiver(mDataErasedReceiver,
                new IntentFilter(DATA_ERASED_ACTION));
    }

    @Override
    public void onResume() {
        super.onResume();
        //if their is already data in the repo, perform action accordingly
        onNewData();
    }

    /**
     * Child classes should override this method to help restoring state
     * or just creating an setting up the internal list adapter.
     */
    protected abstract void setupListAdapter(Bundle savedInstanceState);


    /**
     * The callback function used to populate the ListFragment with items
     * provided by the request.
     *
     * This callback is accepted by the repository layer so is good to use it
     * when trying to get data from there.
     *
     * @param appContext Application's context
     * @return Anonymous implementation of Callback
     */
    protected Completion<List<T>> populateListAdapterCallback(final Context appContext) {
        return new Completion<List<T>>() {
            @Override
            public void onResult(@NonNull CallResult<List<T>> result) {
                try {
                    List<T> aggregator = result.getResult();
                    updateAdapterFromLocalStorage(aggregator);
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                    String msg = getResources().getString(R.string.fetching_info_from_storage);
                    ToastUtils.showError(msg, appContext);
                    onErasedData();
                }
            }
        };
    }

    /**
     * This method notifies the fragment that is time to update its list of items
     * by supplying a new list.
     *
     * Executing this method assumes the adapter was setup before calling this method. Reason is
     * you
     * cannot accept data from others without a container where to put it. Setup the container and
     * then subscribe/ask for events/data.
     *
     * @param newList new list of items
     */
    protected void updateAdapterFromLocalStorage(List<T> newList) {
        AbstractListAdapter adapter = (AbstractListAdapter) getListAdapter();
        for(Identifiable t : newList){
            long newElemID = t.resolveID();
            boolean exists = false;
            for(T residentItem : mItems){
                exists = (residentItem.resolveID() == newElemID);
                if(exists)break;
            }
            if(!exists)
                adapter.add(t);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "OnSaveInstanceState invoked");
        if(mItems != null){
            outState.putParcelableArrayList(SAVE_ITEMS_STATE,
                    (ArrayList<? extends Parcelable>) mItems);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach invoked");
        // Checks if the container activity has implemented the callback interface.
        try {
            mClickedItemCallback = (OnItemSelectedListener) context;
        } catch (ClassCastException e) {
            Log.d(TAG, context.toString() + " must implement OnItemSelectedListener");
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach invoked");
        mClickedItemCallback = null;
    }

    @Override
    public void onStop() {
        super.onStop();
        try {
            getActivity().unregisterReceiver(mDataAvailableReceiver);
            getActivity().unregisterReceiver(mDataErasedReceiver);
        }catch(IllegalArgumentException ignored){}
    }
}
