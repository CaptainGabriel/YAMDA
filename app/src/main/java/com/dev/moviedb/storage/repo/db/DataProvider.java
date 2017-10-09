package com.dev.moviedb.storage.repo.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.util.Log;

import com.dev.moviedb.utils.Utils;


/**
 * The concrete implementation of a ContentProvider. This class performs the actions that
 * act over the data inside the database.
 *
 * This class specifies the authority and the uris accepted by this provider. Given this, other
 * applications can access the data inside this application's database.
 *
 * @version 1.0.0
 */
public class DataProvider extends ContentProvider {

    private static final String TAG = Utils.makeLogTag(DataProvider.class);

    /**
     * A reference to UriMatcher to aid in matching URIs in content providers
     */
    private static final UriMatcher sURIMatcher;

    /**
     * The authority of this provider
     */
    public static final String AUTHORITY = "petegabriel.com.moviedb.yamdaprov";

    /**
     * The basic uri of this provider
     */
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    /*
    These constants mean the codes used for each uri
    when matching against the UriMatcher.
     */
    public static final int FAVORITE_TABLE = 1;
    public static final int UPCOMING_TABLE = 3;
    public static final int POPULAR_TABLE = 5;
    public static final int FAVORITE_TABLE_INDEXED = 2;
    public static final int UPCOMING_TABLE_INDEXED = 4;
    public static final int POPULAR_TABLE_INDEXED = 6;
    public static final int THEATERS_TABLE = 7;
    public static final int THEATERS_TABLE_INDEXED = 8;

    public static final String FAVORITE_TABLE_NAME = "/favorites/";
    private static final String FAVORITE_TABLE_NAME_INDEX = "/favorites/#";

    public static final String UPCOMING_TABLE_NAME = "/upcoming/";
    private static final String UPCOMING_TABLE_NAME_INDEX = "/upcoming/#";

    public static final String POPULAR_TABLE_NAME = "/popular/";
    private static final String POPULAR_TABLE_NAME_INDEX = "/popular/#";

    public static final String THEATERS_TABLE_NAME = "/theaters/";
    private static final String THEATERS_TABLE_NAME_INDEX = "/theaters/#";


    /*
     This procedure is advised by Google when dealing with ContentProviders
     */
    static {
        sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sURIMatcher.addURI(AUTHORITY, FAVORITE_TABLE_NAME, FAVORITE_TABLE);
        sURIMatcher.addURI(AUTHORITY, FAVORITE_TABLE_NAME_INDEX, FAVORITE_TABLE_INDEXED);

        sURIMatcher.addURI(AUTHORITY, UPCOMING_TABLE_NAME, UPCOMING_TABLE);
        sURIMatcher.addURI(AUTHORITY, UPCOMING_TABLE_NAME_INDEX, UPCOMING_TABLE_INDEXED);

        sURIMatcher.addURI(AUTHORITY, POPULAR_TABLE_NAME, POPULAR_TABLE);
        sURIMatcher.addURI(AUTHORITY, POPULAR_TABLE_NAME_INDEX, POPULAR_TABLE_INDEXED);

        sURIMatcher.addURI(AUTHORITY, THEATERS_TABLE_NAME, THEATERS_TABLE);
        sURIMatcher.addURI(AUTHORITY, THEATERS_TABLE_NAME_INDEX, THEATERS_TABLE_INDEXED);
    }

    /**
     * An instance of the database
     */
    private DatabaseHelper db;

    public DataProvider() {}

    @Override
    public boolean onCreate() {
        db = new DatabaseHelper(getContext().getApplicationContext());
        return ((db == null) ? false : true);
    }

    /**
     * For a given uri, get the correspondent database table.
     */
    private String getTableForUri(Uri uri) {
        int i = sURIMatcher.match(uri);
        switch (i) {
            case FAVORITE_TABLE:
            case FAVORITE_TABLE_INDEXED:
                return DataUnit.Tables.FAVORITE_TABLE;

            case UPCOMING_TABLE:
            case UPCOMING_TABLE_INDEXED:
                return DataUnit.Tables.UPCOMING_TABLE;

            case POPULAR_TABLE:
            case POPULAR_TABLE_INDEXED:
                return DataUnit.Tables.POPULAR_TABLE;

            case THEATERS_TABLE:
            case THEATERS_TABLE_INDEXED:
                return DataUnit.Tables.IN_THEATERS_TABLE;

            default:
                return "";
        }
    }



    /**
     * Delete a certain record referenced by the given uri.
     *
     * @param uri           Uri representing the instance or collection.
     * @param selection     WHERE clause.
     * @param selectionArgs Parameters for the where clause.
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        //code that figures out the table to delete from
        String table = getTableForUri(uri);

        int count = db.getWritableDatabase().delete(table, selection, selectionArgs);

        getContext().getContentResolver().notifyChange(uri, null);

        return (count);
    }

    @Override
    public String getType(Uri uri) {
        // implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Insert a new instance of data into the database.
     *
     * @param uri    Uri representing the collection.
     * @param values ContentValues structure with the initial data for the new instance.
     * @return Uri to the new instance.
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        String table = getTableForUri(uri);
        Log.d(TAG, "Resource with uri: " + uri + " for table " + table);

        try{
            long rowID = db.getWritableDatabase().insertOrThrow(table, null, values);
            Log.d(TAG, "INSERT - Inserting resource with uri: " + uri + " in table " + table);

            if (rowID > 0) {
            Uri uriToNewResource = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(uri, null);
            return(uriToNewResource);
        }
        }catch(SQLException _) {
            return null;
        }
        return null;
    }

    /**
     * Perform a query against the database with given parameters.
     *
     * @param uri           A Uri representing the collection or instance being queried.
     * @param projection    A String array representing the list of properties that should be
     *                      returned.
     * @param selection     A String representing what amounts to a SQL WHERE clause, constraining
     *                      which instances should be considered for the query results.
     * @param selectionArgs A String array representing values to “pour into” the WHERE clause,
     *                      replacing
     *                      any '?' found there.
     * @param sortOrder     A String representing what amounts to a SQL ORDER BY clause.
     * @return Cursor that can be used to iterate over and access the data.
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String table = getTableForUri(uri);
        Cursor cursor = db.getReadableDatabase().query(table, projection, selection, selectionArgs, null, null, null);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    /**
     * Perform as update to a resource referenced by the given uri.
     *
     * @param uri           Uri of the instance or collection to change.
     * @param values        ContentValues structure with the new values to apply.
     * @param selection     String for a SQL WHERE clause.
     * @param selectionArgs String array with parameters to use to replace ? found in the WHERE
     *                      clause.
     * @return The amount of records updated.
     */
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        String table = getTableForUri(uri);
        int count = db.getWritableDatabase().update(table, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
