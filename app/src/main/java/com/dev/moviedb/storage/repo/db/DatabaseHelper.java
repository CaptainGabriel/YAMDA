package com.dev.moviedb.storage.repo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * The concrete implementation of {@link SQLiteOpenHelper}
 * that provides a solution to the creation and update of a database
 * inside a device.
 *
 * @version 1.0.0
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    /**
     * The name of the database.
     */
    private static final String DATABASE_NAME = "PDM_DB.db";

    /**
     * The version of the database.
     */
    private static final int DATABASE_VERSION = 8;


    /**
     * ctor
     */
    public DatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /** {@inheritDoc} */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //create all four tables
        db.execSQL(DataUnit.CREATE_FAVORITE_TABLE);
        db.execSQL(DataUnit.CREATE_UPCOMING_TABLE);
        db.execSQL(DataUnit.CREATE_INTHEATERS_TABLE);
        db.execSQL(DataUnit.CREATE_POPULAR_TABLE);
    }

    /** {@inheritDoc} */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //nothing yet
        Log.d("OnUpgrade", "called");
        db.execSQL("DROP TABLE IF EXISTS  " + DataUnit.Tables.FAVORITE_TABLE);
        db.execSQL(DataUnit.CREATE_FAVORITE_TABLE);

        db.execSQL("DROP TABLE IF EXISTS  " + DataUnit.Tables.UPCOMING_TABLE);
        db.execSQL(DataUnit.CREATE_UPCOMING_TABLE);

        db.execSQL("DROP TABLE IF EXISTS " + DataUnit.Tables.IN_THEATERS_TABLE);
        db.execSQL(DataUnit.CREATE_INTHEATERS_TABLE);

        db.execSQL("DROP TABLE IF EXISTS " + DataUnit.Tables.POPULAR_TABLE);
        db.execSQL(DataUnit.CREATE_POPULAR_TABLE);

    }

}
