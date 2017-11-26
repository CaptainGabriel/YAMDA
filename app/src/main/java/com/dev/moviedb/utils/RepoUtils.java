package com.dev.moviedb.utils;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;

import com.dev.moviedb.mvvm.model.movies.AdvancedFact;
import com.dev.moviedb.mvvm.model.movies.Movie;
import com.dev.moviedb.storage.repo.db.DataProvider;
import com.dev.moviedb.storage.repo.db.DataRecord;
import com.dev.moviedb.storage.repo.db.DataUnit;

/**
 * THis class provides various methods that help converting from {@link Movie} into
 * {@link DataRecord} and vice versa.
 *
 * @version 1.0.0
 */
public class RepoUtils {

    private RepoUtils() {
    }


    public static ContentValues getContentValuesOfMovie(final Movie movie) {
        ContentValues values = new ContentValues();
        values.put(DataUnit.ID, movie.getPrimaryFacts().getId());
        values.put(DataUnit.TITLE, movie.getPrimaryFacts().getOriginalTitle());
        values.put(DataUnit.RELEASE_DATE, movie.getReleaseDateFacade());
        values.put(DataUnit.OVERVIEW, movie.getPrimaryFacts().getOverview());
        values.put(DataUnit.BACKDROP_IMAGE, movie.getMovieImages().getBackdropImagePath());
        values.put(DataUnit.POSTER_IMAGE, movie.getMovieImages().getPosterImagePath());
        values.put(DataUnit.VOTE_AVERAGE, movie.getPopularity().getVoteAverage());
        values.put(DataUnit.IS_FAVORITE, false);
        values.put(DataUnit.CREATED_AT, System.currentTimeMillis());
        values.put(DataUnit.TRAILER_URL, movie.getTrailer());
        return values;
    }

    private static ContentValues getContentValuesOfRecord(String tableTarget, DataRecord record) {
        ContentValues values = new ContentValues();
        values.put(DataUnit.ID, record.getId());
        values.put(DataUnit.TITLE, record.getOriginalTitle());
        values.put(DataUnit.RELEASE_DATE, record.getReleaseDate());
        values.put(DataUnit.OVERVIEW, record.getOverview());
        values.put(DataUnit.BACKDROP_IMAGE, record.getBackdropImagePath());
        values.put(DataUnit.POSTER_IMAGE, record.getPosterImagePath());
        values.put(DataUnit.VOTE_AVERAGE, record.getVoteAverage());
        if(!tableTarget.equals(DataProvider.FAVORITE_TABLE_NAME))
            values.put(DataUnit.IS_FAVORITE, record.isFavorite());
        values.put(DataUnit.CREATED_AT, record.getCreatedAt());
        if(!tableTarget.equals(DataProvider.FAVORITE_TABLE_NAME))
            values.put(DataUnit.TRAILER_URL, record.getTrailerUrl());
        return values;
    }

    /**
     * From a given instance of Movie, get an instance of ContentValues to be used
     * when performing actions over the database.
     *
     * This method will fulfil the ContentValues instance only with the fields that belong
     * to the {@link AdvancedFact} part of the Movie instance. These fields are the ones updated
     * when the app asks for details of a certain movie.
     *
     */
    public static ContentValues getDynamicValuesOfMovie(Application ctx, Movie movie) {
        ContentValues values = new ContentValues();
        AdvancedFact facts = movie.getAdvancedFacts();

        values.put(DataUnit.IMDB_ID, facts.getImdbID());
        values.put(DataUnit.RUNTIME, facts.getRuntime());
        values.put(DataUnit.STATUS, facts.getReadableStatus(ctx));
        values.put(DataUnit.TAGLINE, facts.getTagLine());
        values.put(DataUnit.GENRES, facts.getGenres().representation());
        values.put(DataUnit.TRAILER_URL, movie.getTrailer());
        return values;
    }

    /**
     * From a given DataRecord instance, get an instance of ContentValues to be used
     * when performing actions over the database.
     *
     * This method will fulfil the ContentValues instance assuming the instance of
     * DataRecord is not marked as favorite inside the database and is not coming
     * (was not retrieved) from that table as well.
     *
     */
    public static ContentValues getContentValuesOfRecord(DataRecord record){
        return getContentValuesOfRecord(null, record);
    }

    /**
     * From a given DataRecord instance, get an instance of ContentValues to be used
     * when performing actions over the database.
     *
     * This method will fulfil the ContentValues instance assuming the instance of
     * DataRecord is marked as favorite inside the database and is coming (was retrieved)
     * from that table as well.
     *
     */
    public static ContentValues getContentValuesOfFavoriteRecord(DataRecord record){
        return getContentValuesOfRecord(DataProvider.FAVORITE_TABLE_NAME, record);
    }


    @Deprecated
    public static String translateTable(String providerTable){
        switch(providerTable){
            case DataUnit.Tables.FAVORITE_TABLE:
                return DataProvider.FAVORITE_TABLE_NAME;

            case DataUnit.Tables.UPCOMING_TABLE:
                return DataProvider.UPCOMING_TABLE_NAME;

            case DataUnit.Tables.POPULAR_TABLE:
                return DataProvider.POPULAR_TABLE_NAME;

            case DataUnit.Tables.IN_THEATERS_TABLE:
                return DataProvider.THEATERS_TABLE_NAME;
            default:
                return providerTable;
        }
    }

    /**
     * Retrieve a record from a result set. This has to do with the way
     * the columns are setup.
     */
    public static DataRecord getDataRecord(Cursor cursor) {
        DataRecord record = new DataRecord();

        record.setId(cursor.getLong(0));
        record.setOriginalTitle(cursor.getString(1));
        record.setReleaseDate(cursor.getString(2));
        record.setOverview(cursor.getString(3));
        record.setBackdropImagePath(cursor.getString(4));
        record.setPosterImagePath(cursor.getString(5));
        record.setVoteAverage(cursor.getDouble(6));
        record.setFavorite(cursor.getInt(7));
        record.setCreatedAt(cursor.getLong(8));

        if (cursor.getColumnCount() > 9) {
            record.setImdbId(cursor.getString(9));
            record.setRuntime(cursor.getInt(10));
            record.setStatus(cursor.getString(11));
            record.setTagline(cursor.getString(12));
            record.setGenres(cursor.getString(13));
            record.setTrailerUrl(cursor.getString(14));
        }
        return record;
    }


    /**
     * From a given Cursor instance, retrieve a record marked as favorite.
     */
    public static DataRecord getFavoriteRecord(Cursor cursor) {
        DataRecord record = new DataRecord();

        record.setId(cursor.getLong(0));
        record.setOriginalTitle(cursor.getString(1));
        record.setReleaseDate(cursor.getString(2));
        record.setOverview(cursor.getString(3));
        record.setBackdropImagePath(cursor.getString(4));
        record.setPosterImagePath(cursor.getString(5));
        record.setVoteAverage(cursor.getDouble(6));
        record.setCreatedAt(cursor.getLong(7));

        return record;
    }
}
