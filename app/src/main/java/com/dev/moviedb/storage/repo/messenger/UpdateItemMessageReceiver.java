package com.dev.moviedb.storage.repo.messenger;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.dev.moviedb.YamdaApplication;
import com.dev.moviedb.storage.repo.IRepository;
import com.dev.moviedb.utils.Utils;

/**
 * The broadcast receiver that listens for messages sent by components that request
 * an update of data into the local storage.
 *
 * @version 1.0.0
 */
public class UpdateItemMessageReceiver extends BroadcastReceiver {

    private final String TAG  = Utils.makeLogTag(UpdateItemMessageReceiver.class);

    /**
     * The action supported by this receiver.
     */
    public static final String NEW_ITEM_RECEIVED_ACTION = "new.item.received.details.would.be.appreciated";

    /**
     * The key used by this receiver.
     */
    public static final String NEW_ITEM_RECEIVED_KEY = "new.item.received.details.would.be.appreciated.key";

    public UpdateItemMessageReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Just received a new message to update an item inside the repo.");
        //action brings an item. Send it to the service that searches for details.
        Movie itemReceived = intent.getParcelableExtra(NEW_ITEM_RECEIVED_KEY);
        IRepository repo = ((YamdaApplication)context.getApplicationContext()).getRepository();
        repo.updateRecord(itemReceived);
    }
}
