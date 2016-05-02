package com.example.dev.moviedb.storage.repo.db;

/**
 * This class specifies the scripts to create each table of our database.
 * The column names can be accessed through this class as well.
 *
 * @version 1.0.0
 */
public class DataUnit {

    /*
     * Name of each table
     */
    public static class Tables {
        public static final String FAVORITE_TABLE = "FAVORITE_TABLE";
        public static final String UPCOMING_TABLE = "UPCOMING_TABLE";
        public static final String IN_THEATERS_TABLE = "IN_THEATERS_TABLE";
        public static final String POPULAR_TABLE = "POPULAR_TABLE";
    }

    /*
     * The various fields inside tables
     */

    //primary facts
    public static final String OVERVIEW = "OVERVIEW";
    public static final String TITLE = "TITLE";
    public static final String RELEASE_DATE = "RELEASE_DATE";
    public static final String ID = "ID";

    //image's paths
    public static final String BACKDROP_IMAGE = "BACKDROP_IMAGE";
    public static final String POSTER_IMAGE = "POSTER_IMAGE";

    //popularity
    public static final String VOTE_AVERAGE = "VOTE_AVERAGE";

    //dynamic facts
    public static final String GENRES = "GENRES";
    public static final String TAGLINE = "TAGLINE";
    public static final String STATUS = "STATUS";
    public static final String IMDB_ID = "IMDB_ID";
    public static final String RUNTIME = "RUNTIME";

    public static final String TRAILER_URL = "TRAILER_URL";

    //favorite status
    public static final String IS_FAVORITE = "IS_FAVORITE";

    public static final String CREATED_AT = "CREATED_AT";


    /**
     * Statement to create the Upcoming table.
     */
    public static final String CREATE_UPCOMING_TABLE = "CREATE TABLE "
            + Tables.UPCOMING_TABLE
            + " ("
            + " " + ID + " integer PRIMARY KEY,"
            + " " + TITLE + " text,"
            + " " + RELEASE_DATE + " text,"
            + " " + OVERVIEW + " text,"
            + " " + BACKDROP_IMAGE + " text,"
            + " " + POSTER_IMAGE + " text,"
            + " " + VOTE_AVERAGE + " real,"
            + " " + IS_FAVORITE + " numeric,"
            + " " + CREATED_AT + " integer,"
            + " " + IMDB_ID + " text,"
            + " " + RUNTIME + " integer,"
            + " " + STATUS + " text,"
            + " " + TAGLINE + " text,"
            + " " + GENRES + " text,"
            + " " + TRAILER_URL + " text"
            + ")";

    /**
     * Statement to create the In Theaters table.
     */
    public static final String CREATE_INTHEATERS_TABLE = "CREATE TABLE "
            + Tables.IN_THEATERS_TABLE
            + " ("
            + " " + ID + " integer PRIMARY KEY,"
            + " " + TITLE + " text,"
            + " " + RELEASE_DATE + " text,"
            + " " + OVERVIEW + " text,"
            + " " + BACKDROP_IMAGE + " text,"
            + " " + POSTER_IMAGE + " text,"
            + " " + VOTE_AVERAGE + " real,"
            + " " + IS_FAVORITE + " numeric,"
            + " " + CREATED_AT + " integer,"
            + " " + IMDB_ID + " text,"
            + " " + RUNTIME + " integer,"
            + " " + STATUS + " text,"
            + " " + TAGLINE + " text,"
            + " " + GENRES + " text,"
            + " " + TRAILER_URL + " text"
            + ")";

    /**
     * Statement to create the Popular table.
     */
    public static final String CREATE_POPULAR_TABLE = "CREATE TABLE "
            + Tables.POPULAR_TABLE
            + " ("
            + " " + ID + " integer PRIMARY KEY,"
            + " " + TITLE + " text,"
            + " " + RELEASE_DATE + " text,"
            + " " + OVERVIEW + " text,"
            + " " + BACKDROP_IMAGE + " text,"
            + " " + POSTER_IMAGE + " text,"
            + " " + VOTE_AVERAGE + " real,"
            + " " + IS_FAVORITE + " numeric,"
            + " " + CREATED_AT + " integer,"
            + " " + IMDB_ID + " text,"
            + " " + RUNTIME + " integer,"
            + " " + STATUS + " text,"
            + " " + TAGLINE + " text,"
            + " " + GENRES + " text,"
            + " " + TRAILER_URL + " text"
            + ")";

    /**
     * Statement to create the Favorite table.
     */
    public static final String CREATE_FAVORITE_TABLE = "CREATE TABLE "
            + Tables.FAVORITE_TABLE
            + " ("
            + " " + ID + " integer PRIMARY KEY,"
            + " " + TITLE + " text,"
            + " " + RELEASE_DATE + " text,"
            + " " + OVERVIEW + " text,"
            + " " + BACKDROP_IMAGE + " text,"
            + " " + POSTER_IMAGE + " text,"
            + " " + VOTE_AVERAGE + " real,"
            + " " + CREATED_AT + " integer"
            + ")";

}
