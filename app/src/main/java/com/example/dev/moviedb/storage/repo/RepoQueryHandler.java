package com.example.dev.moviedb.storage.repo;

import com.example.dev.moviedb.model.async.CallResult;
import com.example.dev.moviedb.model.async.Completion;
import com.example.dev.moviedb.storage.repo.db.DataRecord;
import com.example.dev.moviedb.utils.RepoUtils;
import com.example.dev.moviedb.utils.Utils;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

/**
 * A concrete implementation of AsyncQueryHandler.
 * {@link android.content.AsyncQueryHandler} helps transforming a synchronous interface
 * into an asynchronous interface.
 *
 * When we call the methods from the ContentResolver class,
 * they are not asynchronous, although every access to the database should be async since we do not
 * know how long the result will take to be available.
 * Using AsyncTask for this kind of thing is kinda of deviating from
 * what they are actually supposed to be used for. Using a AsyncQueryHandler is one solution and a
 * better one.
 *
 * @version 1.0.0
 */
public class RepoQueryHandler  extends AsyncQueryHandler{

    /**
     * When performing queries related to the "Favorite" table, send this as token.
     */
    public static final int HANDLER_FAVORITE_QUERY = 1;

    /**
     * When performing queries NOT related to the "Favorite" table, send this as token.
     */
    public static final int HANDLER_QUERY = 2;


    public final String TAG = Utils.makeLogTag(RepoQueryHandler.class);


    public RepoQueryHandler(ContentResolver cr) {
        super(cr);
    }



    @Override
    protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
        Completion callback = (Completion)cookie;

        switch (token){
            case HANDLER_QUERY:
                invokeCommonCallback(cursor, callback);
                break;
            case HANDLER_FAVORITE_QUERY:
                invokeFavoritesCallback(cursor, callback);
                break;
        }
    }

    @Override
    protected void onInsertComplete(int token, Object cookie, Uri uri) {
        super.onInsertComplete(token, cookie, uri);
        if (uri != null) {
            Log.d(TAG, "Inserted new resource referenced by: " + uri.toString());
        }
    }

    @Override
    protected void onDeleteComplete(int token, Object cookie, int result) {
        super.onDeleteComplete(token, cookie, result);
        if(cookie != null){
            Completion callback = (Completion)cookie;
            callback.onResult(null);
        }
        Log.d(TAG, "Deleted rows: " + result);
    }

    @Override
    protected void onUpdateComplete(int token, Object cookie, int result) {
        super.onUpdateComplete(token, cookie, result);
        Log.d(TAG, "Updated rows: " + result);
    }

    private void invokeFavoritesCallback(Cursor cursor, Completion callback){
        DataRecord record;
        if (cursor.moveToFirst()) {
            record = RepoUtils.getFavoriteRecord(cursor);
            cursor.close();
            callback.onResult(new CallResult<>(record));
        } else {
            cursor.close();
            callback.onResult(new CallResult<>(new Exception("Item not found.")));
        }
    }

    private void invokeCommonCallback(Cursor cursor, Completion callback){
        DataRecord record;
        if (cursor.moveToFirst()) {
            record = RepoUtils.getDataRecord(cursor);
            cursor.close();
            callback.onResult(new CallResult<>(record));
        } else {
            cursor.close();
            callback.onResult(new CallResult<>(new Exception("Item not found.")));
        }
    }

}
